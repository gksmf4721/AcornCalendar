package acorn.calendar.com.member.controller;

import acorn.calendar.config.control.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SecurityMemberController {

    @Autowired
    private final JwtProvider jwtProvider;

    @PostMapping("/securityLogin")
    public void login(HttpServletRequest request, HttpServletResponse response){
        String userId = "test";
        jwtProvider.generateAccessCookie(userId, response);
        jwtProvider.generateRefreshCookie(userId, response);
    }

    @PostMapping("/removeCookie")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        jwtProvider.removeAccessToken(request, response);
    }

}
