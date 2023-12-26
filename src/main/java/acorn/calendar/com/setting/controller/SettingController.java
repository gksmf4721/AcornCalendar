package acorn.calendar.com.setting.controller;

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

@Slf4j
@Controller
@RequiredArgsConstructor
@Tag(name = "Setting", description = "Setting API")
public class SettingController {

	@GetMapping("/mypage.do")
	public String mypage(AcornMap acornMap) throws Exception {
		return "setting/mypage";
	}
}
