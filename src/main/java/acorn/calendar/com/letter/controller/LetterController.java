package acorn.calendar.com.letter.controller;

import acorn.calendar.com.letter.service.LetterService;
import acorn.calendar.com.member.service.MemberService;
import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.model.LoginSession;
import acorn.calendar.config.util.JsonUtils;
import acorn.calendar.config.util.PasswordHashUtils;
import acorn.calendar.config.util.ResponseUtils;
import acorn.calendar.config.util.ValidateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import top.jfunc.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LetterController {

	@Autowired
	private LetterService letterService;

	@RequestMapping("/letterBox.do")
	public String letterBox(AcornMap acornMap, Model model) throws Exception {
		acornMap.put("type","all");
		model.addAttribute("letterList",letterService.selectLetterList(acornMap));
		return "mail/letterBox";
	}

	@RequestMapping("/letterList.json")
	public void letterList(@RequestBody String json, HttpServletResponse response) throws Exception {
		AcornMap acornMap = JsonUtils.toAcornMap(json);
		ResponseUtils.jsonList(response, letterService.selectLetterList(acornMap));
	}

	@RequestMapping("/letterWrite.do")
	public String letterWrite(AcornMap acornMap, Model model) throws Exception {
		return "mail/letterWrite";
	}

	@RequestMapping("/letterWrite.json")
	public void letterWrite(@RequestBody String json,HttpServletResponse response) throws Exception {
		AcornMap acornMap = JsonUtils.toAcornMap(json);
		try{
			acornMap.put("lReciver",letterService.selectSeq(acornMap));
			log.info("결과"+acornMap);
			letterService.insertLetter(acornMap);
		}catch (NullPointerException e){
			e.printStackTrace();
			ResponseUtils.responseMap(response, "-1","없는 회원입니다.",null);
		}
	}

	@RequestMapping("/letterTrash.json")
	public void letterTrash(@RequestBody List<Map<String,Object>> jsonList) throws Exception {
		List<AcornMap> acornList = JsonUtils.toListAcornMap(jsonList);
		letterService.updateTrash(acornList);
	}


}
