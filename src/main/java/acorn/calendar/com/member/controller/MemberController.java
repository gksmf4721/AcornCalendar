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
	public String joinMember(AcornMap acornMap, HttpServletRequest request) throws Exception {

		//AcornMap acornMap = JsonUtils.toAcornMap(json);

		log.info("컨트롤러 폼테스트"+acornMap);
		try {
			//AcornMap acornMap = JsonUtils.toAcornMap(json);
			acornMap.put("mPw", PasswordHashUtils.createHash(acornMap.get("mPw").toString()));
			memberService.insertMember(acornMap);

		}catch(NullPointerException e) {
			log.info("EXCEPTION : THROWS_NULL_POINTER_EXCEPTION");
		}

		return "member/login";
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

	@RequestMapping("/login.do")
	public String login() throws Exception {
		return "member/login";
	}
	
	
}
