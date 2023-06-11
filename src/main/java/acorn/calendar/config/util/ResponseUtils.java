package acorn.calendar.config.util;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import acorn.calendar.config.data.AcornMap;

public class ResponseUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static void jsonString(HttpServletResponse response, String string) throws Exception {
		//response.setContentType("text/plain");
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
//		response.getWriter().write(string);
		response.getWriter().write(string);
	}

	public static void jsonMap(HttpServletResponse response, AcornMap acornMap) throws Exception {
		String data = (new JSONObject(acornMap)).toString();
		jsonString(response, data);
	}
}
