package learningresourcefinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProblemListController {
	
	@RequestMapping("/problemlist")
	public ModelAndView problemListController(@RequestParam("topic") String topic) {
		
		ModelAndView mv = new ModelAndView("problemlist");
		
		return mv;
	}
}
