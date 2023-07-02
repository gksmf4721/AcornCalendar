package acorn.calendar.com.letter.controller;

import acorn.calendar.com.letter.service.LetterService;
import acorn.calendar.com.member.controller.MemberController;
import acorn.calendar.com.member.service.MemberService;
import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.model.LoginSession;
import acorn.calendar.config.util.JsonUtils;
import acorn.calendar.config.util.PasswordHashUtils;
import acorn.calendar.config.util.ResponseUtils;
import acorn.calendar.config.util.ValidateUtils;
import lombok.Data;
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
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LetterController {

	@Autowired
	private LetterService letterService;

	@RequestMapping("/letterBox.do")
	public String letterBox(AcornMap acornMap, Model model, HttpSession session) throws Exception {
		acornMap.put("type","all");

		if(null!=session.getAttribute("trashLetterDelete")){
			acornMap.put("type","trash");
			trashLetterDelete(acornMap,session);
		}

		model.addAttribute("letterList",letterService.selectLetterList(acornMap));


		//test
//		acornMap.put("type","trash");
//		List<AcornMap> list = letterService.selectLetterList(acornMap);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = new Date();
//
//
//		String da = "2023-06-22 09:12:11";
//		Calendar c = Calendar.getInstance();
//		Date te = sdf.parse(da);
//		c.setTime(te);
//		c.add(Calendar.MONTH,1);
//		log.info("데이트 테이스 : "+date);
//		log.info("캘린더 테스트 : "+new Date(c.getTimeInMillis()));
//
//
//
//		for(int i=0 ; i<list.size() ; i++){
//			String trash = list.get(i).getString("SEND").equals("SENDER") ?
//					"L_SENDER_TRASH_DATE": "L_RECIVER_TRASH_DATE";
//			Date trashDate = sdf.parse(list.get(i).getString(trash));
//
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(trashDate);
//			cal.add(Calendar.MONTH,1);
//
//			log.info("현재 : "+date);
//			log.info("리스트 날짜 : "+new Date(cal.getTimeInMillis()));
//
//			if(!date.before(new Date(cal.getTimeInMillis()))){
//				//삭제
//			}
//		}



		return "mail/letterBox";
	}

	private void trashLetterDelete(AcornMap acornMap, HttpSession session) throws Exception {
		session.removeAttribute("trashLetterDelete");
		List<AcornMap> list = letterService.selectLetterList(acornMap);
		List<AcornMap> deleteList = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date nowDate = new Date();

		for(int i=0 ; i<list.size() ; i++){
			String trash = list.get(i).getString("SEND").equals("SENDER") ?
					"L_SENDER_TRASH_DATE": "L_RECIVER_TRASH_DATE";
			Date trashDate = sdf.parse(list.get(i).getString(trash));

			Calendar cal = Calendar.getInstance();
			cal.setTime(trashDate);
			cal.add(Calendar.MONTH,1);

//			log.info("현재 : "+nowDate);
//			log.info("리스트 날짜 : "+new Date(cal.getTimeInMillis()));

			if(!nowDate.before(new Date(cal.getTimeInMillis()))){
				deleteList.add(list.get(i));
			}
		}

		letterService.updateTrashList(deleteList);
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
			letterService.insertLetter(acornMap);
			ResponseUtils.responseMap(response, "1","메일 전송 성공","/letterBox.do");
		}catch (NullPointerException e){
			e.printStackTrace();
			ResponseUtils.responseMap(response, "-1","없는 회원입니다.",null);
		}
	}

	@RequestMapping("/letterTrash.json")
	public void letterTrash(@RequestBody List<Map<String,Object>> jsonList, HttpServletResponse response) throws Exception {
		List<AcornMap> acornList = JsonUtils.toListAcornMap(jsonList);
		try{
			letterService.updateTrash(acornList);
			ResponseUtils.responseMap(response, "1","휴지통으로 이동 되었습니다.","/letterBox.do");
		}catch (Exception e){
			e.printStackTrace();
			ResponseUtils.responseMap(response, "-1","휴지통 이동을 실패했습니다.",null);
		}
	}




}
