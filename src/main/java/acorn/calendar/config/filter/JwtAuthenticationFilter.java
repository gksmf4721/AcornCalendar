package acorn.calendar.config.filter;

import acorn.calendar.config.control.JwtProvider;
import acorn.calendar.config.util.ResponseUtils;
import groovy.util.logging.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private List<String> staticResourcePattern = Arrays.asList("/img/**","/js/**","/css/**");

    public JwtAuthenticationFilter(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 정적 리소스 url 필터에서 제외 예정

        String accessToken = jwtProvider.resolveToken(request, "accessToken");
        String refreshToken = jwtProvider.resolveToken(request, "refreshToken");

        if(accessToken != null && refreshToken != null){
            if(jwtProvider.validateToken(accessToken) && jwtProvider.validateToken(refreshToken)){
                logger.info("success");
                Authentication authentication = jwtProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                logger.info("fail");

                // 컨트롤러의 경우 @RequestMapping 메소드에서 직접 throws Exception 을 선언해서 예외 처리를 안해도 됨.
                // 근데 jsonString 메소드는 throws Exception 이 선언되어 있기에 try - catch 사용해야 함.
                try {
                    ResponseUtils.jsonString(response, "TOKEN IS INVALID");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                return;
            }
        }else{
            logger.info("no token");
            try {
                ResponseUtils.jsonString(response, "TOKEN NOT FOUND");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return;
        }

        filterChain.doFilter(request,response);
    }

    private boolean isAllowedUrl(String requestUri){
        return staticResourcePattern.stream().anyMatch(requestUri::startsWith);
    }
}
