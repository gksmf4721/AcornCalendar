package acorn.calendar.com.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import acorn.calendar.config.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import acorn.calendar.com.member.service.MemberService;
import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.util.PasswordHashUtils;
import acorn.calendar.config.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/")
	public String login() throws Exception {
		return "member/login";
	}

	@RequestMapping("/join.do")
	public String join() throws Exception {
		return "member/join";
	}

	@RequestMapping(value="/join.json" )
	public void joinMember(@RequestBody String json, HttpServletResponse response) throws Exception {

		AcornMap resultMap = new AcornMap();

		try {
			AcornMap acornMap = JsonUtils.toAcornMap(json);

			// "", null, length 유효성 검사

			acornMap.put("mPw", PasswordHashUtils.createHash(acornMap.get("mPw").toString()));
			memberService.insertMember(acornMap);

			resultMap.put("resultMsg","회원가입이 완료됐습니다.");
			resultMap.put("resultCd","1");
			resultMap.put("resultUrl","/");
			ResponseUtils.jsonMap(response,resultMap);

		}catch(Exception e) {
			log.info("EXCEPTION : THROWS_NULL_POINTER_EXCEPTION");
			resultMap.put("resultCd","-1");
			resultMap.put("resultMsg","회원가입에 실패했습니다.");
			ResponseUtils.jsonMap(response,resultMap);
		}
	}

	@RequestMapping("/inputCheck.json")
	public void inputCheck(@RequestBody String json, HttpServletResponse response) throws Exception {

		AcornMap resultMap = JsonUtils.toAcornMap(json);

		String type = resultMap.getString("type");

		int result = memberService.selectInputCheck(resultMap);
log.info("ㅋㅋ"+result);
		if(result != 0){
			resultMap.put("resultCd","-1");
		}else{
			resultMap.put("resultCd","1");
		}
		resultMap.put("resultType",type);
		ResponseUtils.jsonMap(response,resultMap);
	}

	@RequestMapping("/loginCheck.json")
	public void loginCheck(@RequestBody String json, HttpServletResponse response) throws Exception {

		AcornMap acornMap = JsonUtils.toAcornMap(json);

		try{
			AcornMap resultMap = memberService.selectLogin(acornMap);

			if(PasswordHashUtils.validatePassword(acornMap.getString("mPw"), resultMap.getString("M_PW"))){
				resultMap.put("resultMsg","로그인 성공");
				resultMap.put("resultCd","1");
				resultMap.put("resultUrl","/main");
			}else{
				resultMap.clear();
				resultMap.put("resultMsg","비밀번호를 확인해주세요");
				resultMap.put("resultCd","-1");
			}
			ResponseUtils.jsonMap(response,resultMap);
		}catch(Exception e){
			AcornMap failMap = new AcornMap();
			failMap.put("resultMsg","아이디를 확인해주세요");
			failMap.put("resultCd","-1");
			ResponseUtils.jsonMap(response,failMap);
		}
	}
}
