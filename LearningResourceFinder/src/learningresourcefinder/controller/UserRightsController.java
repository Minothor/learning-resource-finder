package learningresourcefinder.controller;


import learningresourcefinder.model.User;
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
public class UserRightsController extends BaseController<User>{
	
	  @Autowired UserRepository userRep;

	   @RequestMapping(value="/rights", method=RequestMethod.GET)
	   public ModelAndView showRightsPage(@RequestParam(value="username",required=false) String userName){
		   
		   User user = null;
		   
		   if(userName != null && userName.trim().length() > 0){
			   user = userRep.getUserByUserName(userName);
		   }else if(SecurityContext.isUserLoggedIn()){
			   user = userRep.getUserByUserName(SecurityContext.getUser().getUserName());
		   }
	       
	       ModelAndView mv = new ModelAndView("userRights");
		   mv.addObject("actions",Action.values());
		   mv.addObject("user",user);
		   
		   return mv;
	   }
	
}
