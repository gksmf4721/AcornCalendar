package acorn.calendar.config.util;

import java.util.regex.Pattern;

import acorn.calendar.config.data.AcornMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

//ValidateUtils���� �˻� (������� �޽��� �����) -> ResponseUtils
@Slf4j
public class ValidateUtils {
	
	public static void resultMap(AcornMap acornMap, String code) {

		String message = MessageUtils.getMessage("code.message."+code);
		//String message = ConfigUtils.getString();
		acornMap.put("resultCode", "-1");
		if(null == message || "".equals(message)) {
			acornMap.put("resultMsg", "code.message.nnn");
		}else {
			acornMap.put("resultMsg", message);
			acornMap.put("resultCode", code);
		}
	}
	
//	public static boolean validate(AcornMap acornMap, String str, String validate) {
//
//		String[] arr = validate.split(",");
//
//		for(int i=0 ; i<arr.length ; i++) {
//			String[] check = arr[i].trim().split(":");
//			if(check.length==2) {
//				String key = check[0];
//				String value = check[1];
//
//				if("required".equals(key) && "true".equals(value)) {
//					if(null==str || "".equals(str)) {
//						resultMap(acornMap,"901");
//						return true;
//					}
//				}else if("maxlength".equals(key) && !"".equals(str)) {
//					if(str.length() > Integer.parseInt(value)) {
//						resultMap(acornMap,"902");
//						return true;
//					}
//				}else if("minlength".equals(key) && !"".equals(str)) {
//					if(str.length() < Integer.parseInt(value)) {
//						resultMap(acornMap,"902");
//						return true;
//					}
//				}else if("email".equals(key) && "true".equals(value) && !"".equals(str)) {
//					if(!Pattern.matches("^[a-zA-Z0-9._-]+@[a-zA-z0-9.-]+\\.[a-zA-Z]{2,4}$", str)) {
//						resultMap(acornMap,"903");
//						return true;
//					}
//				}else if("pwValid".equals(key) && "true".equals(value) && !"".equals(str)){
//					String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!\\()\\[\\]{}\\|;:<>,.?/~_-])[a-zA-Z\\d@#$%^&+=!\\()\\[\\]{}\\|;:<>,.?/~_-]{8,16}$";
//
//					String password_check = acornMap.getString("mPw");
//					boolean isValid = password_check.matches(regex);
//					if(!isValid){
//						resultMap(acornMap,"904");
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
public static String validate(AcornMap acornMap, String str, String validate) {

	String[] arr = validate.split(",");
	String code = "code.message.";

	for(int i=0 ; i<arr.length ; i++) {
		String[] check = arr[i].trim().split(":");
		if(check.length==2) {
			String key = check[0];
			String value = check[1];

			if("required".equals(key) && "true".equals(value)) {
				if(null==str || "".equals(str)) {
					return code+"901";
				}
			}else if("maxlength".equals(key) && !"".equals(str)) {
				if(str.length() > Integer.parseInt(value)) {
					return code+"902";
				}
			}else if("minlength".equals(key) && !"".equals(str)) {
				if(str.length() < Integer.parseInt(value)) {
					return code+"902";
				}
			}else if("email".equals(key) && "true".equals(value) && !"".equals(str)) {
				if(!Pattern.matches("^[a-zA-Z0-9._-]+@[a-zA-z0-9.-]+\\.[a-zA-Z]{2,4}$", str)) {
					return code+"903";
				}
			}else if("pwValid".equals(key) && "true".equals(value) && !"".equals(str)){
				String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!\\()\\[\\]{}\\|;:<>,.?/~_-])[a-zA-Z\\d@#$%^&+=!\\()\\[\\]{}\\|;:<>,.?/~_-]{8,16}$";
				String password_check = acornMap.getString("mPw");
				boolean isValid = password_check.matches(regex);
				if(!isValid){
					return code+"904";
				}
			}
		}
	}
	return "";
}

//	public static AcornMap pwValidMessage(AcornMap acornMap,String code){
//;
//		AcornMap resultMap = new AcornMap();
//
//		String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!\\()\\[\\]{}\\|;:<>,.?/~_-])[a-zA-Z\\d@#$%^&+=!\\()\\[\\]{}\\|;:<>,.?/~_-]{8,16}$";
//
//		String password_check = acornMap.getString("mPw");
//		boolean isValid = password_check.matches(regex);
//		if(!isValid){
//			resultMap.put("validCd","code.message."+code);
//		}else{
//			resultMap.put("validCd","success");
//		}
//
//		return resultMap;
//	}
	
	public static AcornMap fail(AcornMap acornMap) {
		
		AcornMap resultMap = new AcornMap();
		
		resultMap.put("resultCode", -1);
		resultMap.put("resultMap", acornMap.getString("resultMsg"));
		
		return resultMap;
	}
}
