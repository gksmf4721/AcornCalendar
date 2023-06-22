package acorn.calendar.com.main.controller;

import acorn.calendar.com.member.service.MemberService;
import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.model.LoginSession;
import acorn.calendar.config.util.JsonUtils;
import acorn.calendar.config.util.PasswordHashUtils;
import acorn.calendar.config.util.ResponseUtils;
import acorn.calendar.config.util.ValidateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

//	@Autowired
//	private MemberService memberService;
//
//	@Autowired
//	private MessageSource messageSource;

	@RequestMapping("/main.do")
	public String main() throws Exception {
		return "main";
	}


}
