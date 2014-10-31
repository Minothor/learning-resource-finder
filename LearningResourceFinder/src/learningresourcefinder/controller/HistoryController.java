package learningresourcefinder.controller;

import learningresourcefinder.model.User;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.security.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/problem")
public class HistoryController extends BaseController<User> {
	
	@Autowired ProblemRepository problemRepository;
	
	@RequestMapping("/history/problem")
	public ModelAndView problemHistory() {
		ModelAndView mv = new ModelAndView("historyproblem");
		mv.addObject("problemList", problemRepository.findProblemOfAuthor(SecurityContext.getUser()));	
		return mv;
	}
}
