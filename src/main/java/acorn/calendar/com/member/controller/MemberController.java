package acorn.calendar.com.member.controller;

import javax.servlet.http.HttpServletResponse;

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

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private static MemberService memberService;
		
	@RequestMapping("/join.do")
	public String join() throws Exception {
		return "member/join";
	}
	
	@PostMapping(value="/join.json" )
	public void joinMember( AcornMap acornMap, HttpServletResponse response, Model model) throws Exception {
		
		AcornMap resultMap = new AcornMap();
		
		acornMap.put("mPw", PasswordHashUtils.createHash(acornMap.get("mPw").toString()));
				
		try {
			memberService.insertMember(acornMap);
			
			// ��ȿ�� �˻� �߰� ����
			
//			if(result != 1) {
//				resultMap.put("resultCode", -1);
//				resultMap.put("resultMsg", "ȸ�������� �����߽��ϴ�.");
//				ResponseUtils.jsonMap(response, acornMap);
//				return;
//			}
			
			resultMap.put("resultCode", 1);
			resultMap.put("resultMsg", "ȸ�������� �Ϸ��߽��ϴ�.");
			ResponseUtils.jsonMap(response, resultMap);
			
		}catch(NullPointerException e) {
			log.info("EXCEPTION : THROWS_NULL_POINTER_EXCEPTION");
		}
	}
	
	@RequestMapping("/login.do")
	public String login() throws Exception {
		return "member/login";
	}
	
	
}
