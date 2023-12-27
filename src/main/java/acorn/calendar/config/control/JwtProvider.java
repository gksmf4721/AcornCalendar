package acorn.calendar.config.control;

import acorn.calendar.com.member.domain.security.UserDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

//    @Value("${jwt.secret.key}")
//    private String secretKeyString;
    private final long exp = 1000L * 60 * 60;

    private final UserDetailService userDetailService;
    private final Key secretKey = Keys.hmacShaKeyFor("j$wt$S2@ec()j$%A^&c*orn#c!3al3$&41En32*dA=1013*r%@C#R*".getBytes(StandardCharsets.UTF_8));

//    private Key secretKey{
//        return Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
//    }


    // 토큰 생성
    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId); // payload
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + exp))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT 토큰 인증 정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 회원 정보 추출
    public String getUserId(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Request Header
    public String resolveToken(HttpServletRequest request){
        String token = null;
        Cookie cookie = WebUtils.getCookie(request,"accessToken");
        if(cookie != null) token = cookie.getValue();
        return token;
    }

    // 토큰 유효성, 만료 일자 확인
    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception e){
            return false;
        }
    }
}
