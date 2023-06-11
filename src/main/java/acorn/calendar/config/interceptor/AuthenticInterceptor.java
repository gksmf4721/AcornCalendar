package acorn.calendar.config.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import acorn.calendar.config.model.LoginSession;
@Slf4j
public class AuthenticInterceptor extends WebContentInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws ServletException{
		LoginSession loginSession = LoginSession.getLoginSession();
		log.info("Auth 인터셉터");
		
		if(loginSession == null) {
			// ����, xml ���� �� �ۼ� ����
		}
		
		return true;
	}
	
}
