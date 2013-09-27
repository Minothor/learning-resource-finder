package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.Rating;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.RatingRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RessourceListController extends BaseController<Resource> {
	
	@Autowired ResourceRepository resourceRepository;
	@Autowired UserRepository userRepository; //
	@Autowired RatingRepository ratingRepository; //

	@RequestMapping("/ressourcelist")
	public ModelAndView showResourceList () {
		
		ModelAndView mv = new ModelAndView("resourcelist");
		
		List<Resource> listResource = resourceRepository.findAllRessourceOrderByTitle();
		mv.addObject("resourceList", listResource);
		
		if(SecurityContext.isUserLoggedIn()) {
			User user = SecurityContext.getUser();
			mv.addObject("user", user);
			
			List<Rating> listRating = ratingRepository.listRating(listResource, user);
			
			
		}

		return mv;
	}
}
