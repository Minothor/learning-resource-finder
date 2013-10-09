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
//		Test code
//		User user = SecurityContext.getUser();
//		if (user != null) {
//			user.setUserProgressPoints(user.getUserProgressPoints() + 3);
//		}
		return "progressbar";
	}

}
