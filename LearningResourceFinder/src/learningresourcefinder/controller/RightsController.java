package learningresourcefinder.controller;


import learningresourcefinder.model.User;
import learningresourcefinder.repository.ContributionRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.Action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller()
public class RightsController extends BaseController<User>{
	
	  @Autowired UserRepository userRepository;
	  @Autowired ContributionRepository contributionRepository;

	   @RequestMapping(value="/rights", method=RequestMethod.GET)
	   public ModelAndView rights(@RequestParam(value="username",required=false) String userName){
		   
		   User user = null;
		   
		   if(userName != null && userName.trim().length() > 0){
			   user = userRepository.getUserByUserName(userName);
		   } else if (SecurityContext.isUserLoggedIn()) {
			   user = SecurityContext.getUser();
		   } else {  // No user ??
			   
		   }
	       
	       ModelAndView mv = new ModelAndView("rights");
		   mv.addObject("actions", Action.values());
		   mv.addObject("user",user);
		   
		   if (user != null) {
			   mv.addObject("contributions", contributionRepository.findAllByUser(user));
			   mv.addObject("contributionsPoints",contributionRepository.sumByUser(user));
			   mv.addObject("levelProgress",user.getUserProgressPoints());
		   }
		   
		   return mv;
	   }
	
}
