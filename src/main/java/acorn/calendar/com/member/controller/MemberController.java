package acorn.calendar.com.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import acorn.calendar.config.model.LoginSession;
import acorn.calendar.config.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

	@Autowired
	private MemberService memberService;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@RequestMapping("/")
	public String login() throws Exception {
		if (null != SessionUtils.getSession(true).getAttribute("login.session")) {
			return "redirect:/main.do";
		}
		return "member/login";
	}

	@RequestMapping("/logout.do")
	public String logout() throws Exception {
		SessionUtils.removeAttribute("login.session");
		return "redirect:/";
	}

	@RequestMapping("/join.do")
	public String join() throws Exception {
		return "member/join";
	}

	@RequestMapping("/find.do")
	public String find(AcornMap acornMap) throws Exception {
		if (acornMap.getString("type").equals("I")) {
			return "member/findId";
		}
		return "member/findPw";
	}

	@RequestMapping(value = "/join.json")
	public void joinMember(@RequestBody String json, HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		try {
			AcornMap acornMap = JsonUtils.toAcornMap(json);

			String valCd = ValidateUtils.validate(acornMap, acornMap.getString("mPw"), "pwValid:true");
			if (!"".equals(valCd)) {
				String valMsg = ValidateUtils.validMsg(valCd);
				ResponseUtils.responseMap(response, "-1", valMsg, "");
				return;
			}
			if (!acornMap.getString("mPwChk").equals(acornMap.getString("mPw"))) {
				ResponseUtils.responseMap(response, "-1", ValidateUtils.validMsg("join.check.password"), "");
				return;
			}

			// BCrypt 적용
			//acornMap.put("mPw",passwordEncoder.encode(acornMap.get("mPw").toString()));

			acornMap.put("mPw", PasswordHashUtils.createHash(acornMap.get("mPw").toString()));
			memberService.insertMember(acornMap, request);

			ResponseUtils.responseMap(response, "1", ValidateUtils.validMsg("join.success"), "/");

		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtils.responseMap(response, "-1", ValidateUtils.validMsg("join.fail"), "");
		}
	}

	@RequestMapping("/inputCheck.json")
	public void inputCheck(@RequestBody String json, HttpServletResponse response) throws Exception {

		AcornMap resultMap = JsonUtils.toAcornMap(json);
		String type = resultMap.getString("type");

		int result = memberService.selectInputCheck(resultMap);
		if (result != 0) {
			resultMap.put("resultCd", "-1");
		} else {
			resultMap.put("resultCd", "1");
		}
		resultMap.put("resultType", type);
		ResponseUtils.jsonMap(response, resultMap);
	}

	@RequestMapping("/loginCheck.json")
	public void loginCheck(@RequestBody String json, HttpServletResponse response, HttpSession session)
			throws Exception {

		AcornMap acornMap = JsonUtils.toAcornMap(json);

		try {
			AcornMap resultMap = memberService.selectLogin(acornMap);

			if (null == resultMap) {
				throw new Exception("NOT_FOUND_MEMBER_ID");
			}

			// security
			//if (passwordEncoder.matches(acornMap.getString("mPw", resultMap.getString("M_PW")))) {
			if (PasswordHashUtils.validatePassword(acornMap.getString("mPw"), resultMap.getString("M_PW"))) {
				LoginSession.setLoginSession(resultMap);
				session.setAttribute("trashLetterDelete", true);
				resultMap.put("resultCd", "1");
				resultMap.put("resultUrl", "/main.do");
			} else {
				resultMap.clear();
				resultMap.put("resultMsg", "비밀번호를 확인해주세요");
				resultMap.put("resultCd", "-1");
			}
			ResponseUtils.jsonMap(response, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			AcornMap failMap = new AcornMap();
			failMap.put("resultMsg", "아이디를 확인해주세요");
			failMap.put("resultCd", "-1");
			ResponseUtils.jsonMap(response, failMap);
		}
	}
}
