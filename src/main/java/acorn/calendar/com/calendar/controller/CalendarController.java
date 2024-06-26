package acorn.calendar.com.calendar.controller;

import acorn.calendar.com.calendar.domain.dto.CalendarDTO;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import acorn.calendar.com.calendar.service.CalendarContService;
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
import java.text.ParseException;

@Slf4j
@Controller
@RequiredArgsConstructor
@Tag(name = "Calendar", description = "Calendar API")
public class CalendarController {

	private final CalendarService calendarService;
	private final CalendarContService calendarContService;

	@RequestMapping("/calendarList.do")
	public String join(AcornMap acornMap, Model model) throws Exception {
		AcornMap calendar = calendarService.selectMyCalendar(Long.parseLong(acornMap.getString("session_m_seq")));
		model.addAttribute("calendar", calendar);
		model.addAttribute("data", acornMap);
		return "calendar/calendarList";
	}

	@ApiOperation(tags = "Calendar", value = "캘린더 추가", notes = "캘린더 추가")
	@PostMapping(value = "/calendar.json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<RestResponse.RestResultResponse> calendar(
			@RequestBody CalendarVO.Jh_Cal_Calendar requestBody) {
		calendarService.insertCalendar(requestBody);
		return ResponseEntity.ok(RestResponse.RestResultResponse.builder().build());
	}

	@ApiOperation(tags = "Cont", value = "일정 추가", notes = "일정 추가")
	@PostMapping(value = "/cont.json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<RestResponse<Double>> cont(
			@RequestBody CalendarVO.Jh_Cal_Cont_Calendar requestBody) throws ParseException {
		Double vactCnt = calendarContService.insertCalendarCont(requestBody);
		return ResponseEntity.ok(new RestResponse<Double>(vactCnt));
	}

	@ApiOperation(tags = "Cont", value = "일정 수정", notes = "일정 수정")
	@PostMapping(value = "/contUpdate.json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<RestResponse<Double>> contUpdate(
			@RequestBody CalendarVO.Jh_Cal_Cont_Calendar requestBody) throws ParseException {
		Double vactCnt = calendarContService.updateCalendarCont(requestBody);
		return ResponseEntity.ok(new RestResponse<Double>(vactCnt));
	}

	@ApiOperation(tags = "Cont", value = "일정 삭제", notes = "일정 삭제")
	@PostMapping(value = "/contDelete.json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<RestResponse<Double>> contDelete(
			@RequestBody CalendarVO.Jh_Cal_Cont_Calendar requestBody) throws ParseException {
		Double vactCnt = calendarContService.deleteCalednarCont(requestBody);
		return ResponseEntity.ok(new RestResponse<Double>(vactCnt));
	}

	@ApiOperation(tags = "Calendar", value = "캘린더 조회", notes = "캘린더 조회")
	@GetMapping(value = "/calendar.json/{mSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse<CalendarDTO.Jh_Cal_Calendar_ListResponse>> calendar(@PathVariable long mSeq) {
		CalendarDTO.Jh_Cal_Calendar_ListResponse response = calendarService.selectCalendar(mSeq);
		return ResponseEntity.ok(new RestResponse<CalendarDTO.Jh_Cal_Calendar_ListResponse>(response));
	}

	@ApiOperation(tags = "Cont", value = "일정 조회", notes = "일정 조회")
	@GetMapping(value = "/cont.json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse<CalendarDTO.Jh_Cal_Cont_Calendar_ListResponse>> selectCont(
			@RequestParam("calSeq") long calSeq, @RequestParam("mSeq") long mSeq,
			@RequestParam("contStartDt") String contStartDt, @RequestParam("contEndDt") String contEndDt)
			throws ParseException {
		CalendarDTO.Jh_Cal_Cont_Calendar_ListResponse response = calendarContService.selectCalendarCont(calSeq, mSeq,
				contStartDt, contEndDt);
		return ResponseEntity.ok(new RestResponse<CalendarDTO.Jh_Cal_Cont_Calendar_ListResponse>(response));
	}

}
