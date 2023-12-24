package acorn.calendar.com.calendar.controller;

import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import acorn.calendar.com.calendar.service.CalendarService;
import acorn.calendar.config.data.RestResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@Tag(name = "Calendar", description = "Calendar API")
public class CalendarController {

	private final CalendarService calendarService;

	@RequestMapping("/calendarList.do")
	public String join(AcornMap acornMap, Model model) throws Exception {
		model.addAttribute("data",acornMap);
		return "calendar/calendarList";
	}


	@ApiOperation(tags = "Calendar", value = "캘린더 추가", notes = "캘린더 추가")
	@PostMapping(value = "/calendar.json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<RestResponse.RestResultResponse> calendar(@RequestBody CalendarVO.Jh_Cal_Calendar requestBody){
		calendarService.insertCalendar(requestBody);
		return ResponseEntity.ok(RestResponse.RestResultResponse.builder().build());
	}

}
