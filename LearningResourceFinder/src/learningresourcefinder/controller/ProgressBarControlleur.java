package learningresourcefinder.controller;

import learningresourcefinder.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProgressBarControlleur extends BaseController<User> {
	
	@RequestMapping("/ajax/progressbar")
	public String progressBar(){
		return "progressbar";
	}

}
