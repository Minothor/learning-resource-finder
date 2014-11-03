package learningresourcefinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResourceExpendController {
	
	 @RequestMapping("/ajax/expendresourceinfo")
	    public ModelAndView getMoreResources(@RequestParam("resourceId") Long resourceId) {
		 return new ModelAndView("resourceexpend")
				 .addObject("resourceId", resourceId);
	 }
}
