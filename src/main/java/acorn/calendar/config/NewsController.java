package acorn.calendar.config;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NewsController {
	
	private final NewsService newsService;
	
	@RequestMapping("/index")
	public String index(Model model, AcornMap acornMap) throws Exception {
		List<HashMap<String,Object>> list = newsService.testt();
		model.addAttribute("test",list);
		log.info("==========test==========");
		return "indexx";
	}
	
	@RequestMapping("/test")
	public void test(AcornMap acornMap, HttpServletRequest request) throws Exception {
	}

}	