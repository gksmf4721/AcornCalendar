package acorn.calendar.config.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.model.LoginSession;

public class RequestUtils {
	
	public static AcornMap getParamMap(HttpServletRequest request) {
		
		AcornMap acornMap = new AcornMap();
		
		Enumeration<?> keys = request.getParameterNames();
		
		while(keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			String[] val = request.getParameterValues(key);
			
			if(val == null) {
				acornMap.put(key, "");
			}else if(val.length==1) {
				acornMap.put(key, val[0]);
			}else {
				acornMap.put(key, new ArrayList(Arrays.asList(val)));
				//acornMap.put(key, val);
			}
		}
		
		//모든 파라미터에 로그인 정보 붙게 함.
		LoginSession login = LoginSession.getLoginSession();
		if(login!=null) {
			acornMap.put("sessionLogin", "Y");
			acornMap.put("session_m_seq", login.getM_seq());
			acornMap.put("session_m_name", login.getM_name());
			acornMap.put("session_m_id", login.getM_id());
			acornMap.put("session_m_nickname", login.getM_nickname());
			acornMap.put("session_m_email", login.getM_email());
			acornMap.put("session_m_birth", login.getM_birth());
			acornMap.put("session_m_birth_yn", login.getM_birth_yn());
			acornMap.put("session_m_del_yn", login.getM_del_yn());			
		}else {
			acornMap.put("sessionLogin", "N");
			acornMap.remove("session_m_seq");
			acornMap.remove("session_m_name");
			acornMap.remove("session_m_id");
			acornMap.remove("session_m_nickname");
			acornMap.remove("session_m_email");
			acornMap.remove("session_m_birth");
			acornMap.remove("session_m_birth_yn");
			acornMap.remove("session_m_del_yn");			
		}
		
		return acornMap;
	}
}
