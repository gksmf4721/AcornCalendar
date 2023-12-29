package acorn.calendar.config.control;

import acorn.calendar.com.member.domain.security.UserDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String secretKeyValue;
    private final long accessExpireTime = 1000L * 60 * 30; // 30분
    private final long refreshExpireTime = 1000L * 60 * 60 * 24; // 24시간

    private final UserDetailService userDetailService;
    private Key secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(secretKeyValue.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String generateAccessToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId); // payload
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT 토큰 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 회원 정보 추출
    public String getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Request Header
    public String resolveToken(HttpServletRequest request, String tokenName) {
        String token = null;
        Cookie cookie = WebUtils.getCookie(request,tokenName);
        if(cookie != null) token = cookie.getValue();
        return token;
    }

    // 토큰 유효성, 만료 일자 확인
    public boolean validateToken(String jwtToken) {
        try{
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception e){
            return false;
        }
    }

    // AccessToken 생성
    public void generateAccessCookie(String userId, HttpServletResponse response) {
        String accessToken = this.generateAccessToken(userId);
        System.out.println("엑세스 토큰 : "+accessToken);
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge((int)accessExpireTime);
        response.addCookie(cookie);
    }

    // RefreshToken 생성
    public void generateRefreshCookie(String userId, HttpServletResponse response) {
        String refreshToken = this.generateRefreshToken(userId);
        System.out.println("리프레쉬 토큰 : "+refreshToken);
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge((int)refreshExpireTime);
        response.addCookie(cookie);
    }

    // 아직 프론트단 구현 X. 임시 출력용 메소드
    public void tempPrintCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie c : cookies){
                if("accessToken".equals(c.getName())){
                    System.out.println("accessToken : "+c.getValue());
                }
                if("refreshToken".equals(c.getName())){
                    System.out.println("refreshToken : "+c.getValue());
                }
            }
        }else{
            System.out.println("쿠키 없음");
        }
    }

    public void removeAccessToken(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("accessToken".equals(c.getName())) {
                    c.setValue(null);
                    c.setMaxAge(0);
                    c.setPath("/");
                    response.addCookie(c);
                    break;
                }
            }
        }
    }


}
