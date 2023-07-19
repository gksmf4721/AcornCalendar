package acorn.calendar.com.calendar.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CalendarController {

	@RequestMapping("/calendarList.do")
	public String join() throws Exception {
		return "calendar/calendarList";
	}
}
