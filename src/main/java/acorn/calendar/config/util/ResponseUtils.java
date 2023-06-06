package acorn.calendar.config.util;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import acorn.calendar.config.data.AcornMap;

public class ResponseUtils {
	
	public static void jsonString(HttpServletResponse response, String string) throws Exception {
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(string);
	}
	
	public static void jsonMap(HttpServletResponse response, AcornMap acornMap) throws Exception {
		String data = (new JSONObject(acornMap)).toString();
		jsonString(response, data);
	}
}
