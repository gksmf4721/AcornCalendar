package acorn.calendar.config.util;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import acorn.calendar.config.data.AcornMap;

import java.util.ArrayList;
import java.util.List;

public class ResponseUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static void responseMap(HttpServletResponse response, String cd, String msg, String url) throws Exception {
		AcornMap resultMap = new AcornMap();
		resultMap.put("resultCd",cd);
		resultMap.put("resultMsg",msg);
		resultMap.put("resultUrl",url);
		jsonMap(response,resultMap);
	}
	public static void responseMap(HttpServletResponse response, String cd, String msg, String url, String data) throws Exception {
		AcornMap resultMap = new AcornMap();
		resultMap.put("resultCd",cd);
		resultMap.put("resultMsg",msg);
		resultMap.put("resultUrl",url);
		resultMap.put("resultData",data);
		jsonMap(response,resultMap);
	}

	public static void jsonString(HttpServletResponse response, String string) throws Exception {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(string);
	}

	public static void jsonMap(HttpServletResponse response, AcornMap acornMap) throws Exception {
		String data = (new JSONObject(acornMap)).toString();
		jsonString(response, data);
	}

	public static void jsonList(HttpServletResponse response, List<AcornMap> jsonList) throws Exception {
		JSONArray jsonArray = new JSONArray();
		for (AcornMap map : jsonList) {
			JSONObject jsonMap = new JSONObject(map);
			jsonArray.put(jsonMap);
		}
		String jsonString = jsonArray.toString();
		jsonString(response, jsonString);
	}

}
