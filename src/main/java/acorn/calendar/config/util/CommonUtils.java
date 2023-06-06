package acorn.calendar.config.util;

import acorn.calendar.config.data.AcornMap;

public class CommonUtils {
	
	public static AcornMap resultMessage(int result) {
		return resultMessage(result,"정상적으로 처리되었습니다.");
	}
	
	public static AcornMap resultMessage(int result, String success) {
		AcornMap resultMap = new AcornMap();
		if(result > 0) {
			resultMap.put("resultCode", "1");
			resultMap.put("resultMsg", success);
		}else {
			resultMap.put("resultCode", "-1");
			resultMap.put("resultMsg", "실패하였습니다.");
		}
		
		return resultMap;
	}
	
	//사용 ex)
	// AcornMap resultMap = CommonUtils.resultMap(-1,"입력한 패스워드가 기존과 다릅니다.");
	// ResponseUtils.jsonMap(response,resultMap);
	// return;
	public static AcornMap resultMap(int resultCode, String message) {
		AcornMap resultMap = new AcornMap();
		resultMap.put("resultCode",resultCode);
		resultMap.put("resultMsg", message);
		return resultMap;
	}
}
