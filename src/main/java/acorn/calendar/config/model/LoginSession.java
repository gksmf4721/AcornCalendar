package acorn.calendar.config.model;

import java.io.Serializable;

import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.util.SessionUtils;
import lombok.Data;

@Data
public class LoginSession implements Serializable {
	
	private static final long serialVersionUID = 4238947983471374L;
	private static final String LOGIN_SESSION = "login.session";
	
	private String m_seq;
	private String m_name;
	private String m_id;
	private String m_nickname;
	private String m_email;
	private String m_birth;
	private String m_birth_yn;
	private String m_del_yn;
	
	public static void setLoginSession(AcornMap acornMap) {
		
		LoginSession login = new LoginSession();
		
		login.setM_seq(acornMap.getString("M_SEQ"));
		login.setM_name(acornMap.getString("M_NAME"));
		login.setM_id(acornMap.getString("M_ID"));
		login.setM_nickname(acornMap.getString("M_NICKNAME"));
		login.setM_email(acornMap.getString("M_EMAIL"));
		login.setM_birth(acornMap.getString("M_BIRTH"));
		login.setM_birth_yn(acornMap.getString("M_BIRTH_YN"));
		login.setM_del_yn(acornMap.getString("M_DEL_YN"));
		
		SessionUtils.getSession(true).setAttribute(LOGIN_SESSION, login);
	}
	
	public static LoginSession getLoginSession() {
		return (LoginSession)SessionUtils.getSession(true).getAttribute(LOGIN_SESSION);
	}
	
}
