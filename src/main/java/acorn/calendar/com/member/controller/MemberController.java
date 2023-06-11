package acorn.calendar.com.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import acorn.calendar.config.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@RequestMapping("/join.do")
	public String join() throws Exception {
		return "member/join";
	}

	@PostMapping(value="/join.json" )
	public void joinMember(@RequestBody String json, HttpServletResponse response) throws Exception {

		AcornMap resultMap = new AcornMap();

		try {
			AcornMap acornMap = JsonUtils.toAcornMap(json);


			acornMap.put("mPw", PasswordHashUtils.createHash(acornMap.get("mPw").toString()));
			memberService.insertMember(acornMap);

			resultMap.put("resultMsg","회원가입이 완료됐습니다.");
			resultMap.put("resultCd", "1");
			resultMap.put("resultUrl","/");
			ResponseUtils.jsonMap(response,resultMap);

		}catch(Exception e) {
			log.info("EXCEPTION : THROWS_NULL_POINTER_EXCEPTION");
			resultMap.put("resultMsg","회원가입에 실패했습니다.");
			resultMap.put("resultCd", "-1");
			ResponseUtils.jsonMap(response,resultMap);
		}
	}


//	@PostMapping(value="/join.json" )
//	public String joinMember(AcornMap acornMap) throws Exception {
//
//		try {
//			acornMap.put("mPw", PasswordHashUtils.createHash(acornMap.get("mPw").toString()));
//			memberService.insertMember(acornMap);
//
//		}catch(NullPointerException e) {
//			log.info("EXCEPTION : THROWS_NULL_POINTER_EXCEPTION");
//		}
//
//		return "member/login";
//	}

	@RequestMapping("/")
	public String login() throws Exception {
		return "member/login";
	}
	
	
}
