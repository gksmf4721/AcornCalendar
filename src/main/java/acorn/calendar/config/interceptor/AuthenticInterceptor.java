package acorn.calendar.config.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import acorn.calendar.com.member.service.MemberService;
import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.util.ContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import acorn.calendar.config.model.LoginSession;
@Slf4j
public class AuthenticInterceptor extends WebContentInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws ServletException{
		LoginSession loginSession = LoginSession.getLoginSession();
		log.info("Auth Interceptor : "+loginSession);

		if(loginSession!=null){
			MemberService service = (MemberService) ContextUtils.getBean("memberService");
			AcornMap param = new AcornMap();
			param.put("mId",loginSession.getM_id());
			try{
				AcornMap member = service.selectLogin(param);
				LoginSession.setLoginSession(member);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
