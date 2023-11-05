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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.jfunc.json.Json;
import top.jfunc.json.JsonArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
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
		if(null!=session.getAttribute("trashLetterDelete")){
			acornMap.put("type","trash");
			trashLetterDelete(acornMap,session);
		}
		acornMap.put("type","all");
		model.addAttribute("letterList",letterService.selectLetterList(acornMap));

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

			if(!nowDate.before(new Date(cal.getTimeInMillis()))){
				deleteList.add(list.get(i));
			}
		}
		letterService.updateTrashList(deleteList);
	}

	@RequestMapping("/letterList.json")
	public void letterList(@RequestBody String json, HttpServletResponse response) throws Exception {
		AcornMap acornMap = JsonUtils.toAcornMap(json);
		AcornMap map = new AcornMap();
		map.put("data",letterService.selectLetterList(acornMap));
		ResponseUtils.jsonMap(response, map);
	}

	@RequestMapping("/letterWrite.do")
	public String letterWrite() throws Exception {
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
	public void letterTrash(@RequestBody String json, HttpServletResponse response) throws Exception {
		List<AcornMap> acornList = JsonUtils.toListAcornMap(json);
		try{
			letterService.updateTrash(acornList);
			ResponseUtils.responseMap(response, "1","휴지통으로 이동 되었습니다.","/letterBox.do");
		}catch (Exception e){
			e.printStackTrace();
			ResponseUtils.responseMap(response, "-1","휴지통 이동을 실패했습니다.",null);
		}
	}

	@RequestMapping("/letterTrashDelete.json")
	public void letterTrashDelete(@RequestBody String json, HttpServletResponse response) throws Exception {
		List<AcornMap> acornList = JsonUtils.toListAcornMap(json);
		try{
			letterService.updateTrashDelete(acornList);
			ResponseUtils.responseMap(response, "1","삭제를 완료했습니다.","/letterBox.do");
		}catch (Exception e){
			e.printStackTrace();
			ResponseUtils.responseMap(response, "-1","삭제를 실패했습니다.",null);
		}
	}

	@RequestMapping("/letterTrashRestore.json")
	public void letterTrashRestore(@RequestBody String json, HttpServletResponse response) throws Exception {
		List<AcornMap> acornList = JsonUtils.toListAcornMap(json);
		try{
			letterService.updateTrashRestore(acornList);
			ResponseUtils.responseMap(response, "1","복구를 완료했습니다.","/letterBox.do");
		}catch (Exception e){
			e.printStackTrace();
			ResponseUtils.responseMap(response, "-1","복구를 실패했습니다.",null);
		}
	}
}