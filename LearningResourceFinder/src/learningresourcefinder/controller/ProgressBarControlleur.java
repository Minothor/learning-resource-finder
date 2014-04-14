package learningresourcefinder.controller;

import learningresourcefinder.model.User;
import learningresourcefinder.security.SecurityContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

@Controller
public class ProgressBarControlleur extends BaseController<User> {
	
	@RequestMapping("/ajax/progressbar")
	public String progressBar(){
		return "progressbar";
	}

}
