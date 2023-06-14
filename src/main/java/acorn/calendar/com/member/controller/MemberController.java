package acorn.calendar.com.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import acorn.calendar.config.model.LoginSession;
import acorn.calendar.config.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import acorn.calendar.com.member.service.MemberService;
import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MessageSource messageSource;

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

		try {
			AcornMap acornMap = JsonUtils.toAcornMap(json);

			String valCd = ValidateUtils.validate(acornMap,acornMap.getString("mPw"),"pwValid:true");
			if(!"".equals(valCd)){
				String valMsg = validMsg(valCd);
				ResponseUtils.responseMap(response,"-1",valMsg,"");
				return;
			}
			if(!acornMap.getString("mPwChk").equals(acornMap.getString("mPw"))){
				ResponseUtils.responseMap(response,"-1",validMsg("join.check.password"),"");
				return;
			}

			acornMap.put("mPw", PasswordHashUtils.createHash(acornMap.get("mPw").toString()));
			memberService.insertMember(acornMap);

			ResponseUtils.responseMap(response,"1",validMsg("join.success"),"/");

		}catch(Exception e) {
			e.printStackTrace();
			ResponseUtils.responseMap(response,"-1",validMsg("join.fail"),"");
		}
	}

	public String validMsg(String code) throws Exception{
		return messageSource.getMessage(code,null,Locale.KOREA);
	}

	@RequestMapping("/inputCheck.json")
	public void inputCheck(@RequestBody String json, HttpServletResponse response) throws Exception {

		AcornMap resultMap = JsonUtils.toAcornMap(json);
		String type = resultMap.getString("type");

		int result = memberService.selectInputCheck(resultMap);
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

			if(null == resultMap){throw new Exception("NOT_FOUND_MEMBER_ID");}

			if(PasswordHashUtils.validatePassword(acornMap.getString("mPw"), resultMap.getString("M_PW"))){
				LoginSession.setLoginSession(resultMap);
				resultMap.put("resultCd","1");
				resultMap.put("resultUrl","/main.do");
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
