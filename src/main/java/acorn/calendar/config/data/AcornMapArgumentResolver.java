package acorn.calendar.config.data;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import acorn.calendar.config.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import acorn.calendar.config.util.RequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class AcornMapArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return AcornMap.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		AcornMap acornMap = new AcornMap();
		acornMap = RequestUtils.getParamMap(request);

		// Ajax - AcornMap 변환 (파라미터 값을 AcornMap acornMap 으로 작성 가능)
//		ServletInputStream inputStream = request.getInputStream();
//		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
//		AcornMap test = JsonUtils.toAcornMap(messageBody);

		return acornMap;
	}
	
	
}
