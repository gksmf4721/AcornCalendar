package acorn.calendar.config.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.WebContentInterceptor;

import acorn.calendar.config.model.LoginSession;

public class AuthenticInterceptor extends WebContentInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws ServletException{
		LoginSession loginSession = LoginSession.getLoginSession();
		
		if(loginSession == null) {
			// 쿼리, xml 생성 후 작성 예정
		}
		
		return true;
	}
	
}
