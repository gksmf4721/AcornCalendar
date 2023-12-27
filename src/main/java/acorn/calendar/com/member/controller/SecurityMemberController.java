package acorn.calendar.com.member.controller;

import acorn.calendar.config.control.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class SecurityMemberController {

    @Autowired
    private final JwtProvider jwtProvider;

    @PostMapping("/securityLogin")
    public void login(HttpServletResponse response){
        String token = jwtProvider.createToken("test");
        Cookie cookie = new Cookie("accessToken",token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
