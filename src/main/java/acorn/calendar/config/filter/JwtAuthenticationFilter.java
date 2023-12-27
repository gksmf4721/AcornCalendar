package acorn.calendar.config.filter;

import acorn.calendar.config.control.JwtProvider;
import io.jsonwebtoken.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private List<String> staticResourcePattern = Arrays.asList("/img/**","/js/**","/css/**");

    public JwtAuthenticationFilter(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 포스트맨으로 쿠키 생성해서 실제 브라우저에는 쿠키가 없음.
        // 정적 리소스 url은 필터에서 제외 예정

        String token = jwtProvider.resolveToken(request);
        if(token != null && jwtProvider.validateToken(token)){
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            // 토큰 없을 시 처리
        }
        filterChain.doFilter(request,response);
    }

    private boolean isAllowedUrl(String requestUri){
        return staticResourcePattern.stream().anyMatch(requestUri::startsWith);
    }
}
