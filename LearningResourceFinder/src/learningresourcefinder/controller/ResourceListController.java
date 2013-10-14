package learningresourcefinder.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import learningresourcefinder.exception.InvalidUrlException;
import learningresourcefinder.model.Rating;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.RatingRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.service.LevelService;
import learningresourcefinder.util.Action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResourceListController extends BaseController<Resource> {
	
	@Autowired ResourceRepository resourceRepository;
	@Autowired UserRepository userRepository; //
	@Autowired RatingRepository ratingRepository; //
	@Autowired LevelService levelService;

	@RequestMapping("/ressourcelist/{userName}")
	public ModelAndView resourceList(@PathVariable("userName") String userName) {
		
		User user = userRepository.getUserByUserName(userName);
        if (user == null) {
        	throw new InvalidUrlException("L'utilisateur ayant le pseudonyme (userName) '"+userName+"' est introuvable.");
        }
       
        boolean canvote = false;
        canvote = levelService.canDoAction(user, Action.VOTE);
        ModelAndView mv = new ModelAndView("resourcelist");

        List<Resource> listResource = resourceRepository.findAllResourceByUser(user);
        List<Rating> listRating = ratingRepository.listRating(listResource, user);

        Map<Resource, Rating> mapRating = new HashMap<Resource, Rating>();

        for(Rating rating : listRating) {
        	mapRating.put(rating.getResource(), rating);
        }

        mv.addObject("resourceList", listResource);
        mv.addObject("mapRating", mapRating);
        mv.addObject("user", user);
        mv.addObject("usercanvote",canvote);
        
		return mv;
	
	}
	
	@RequestMapping("/problemresourcelist/{topic}")
	public ModelAndView problemresourcelist(@PathVariable("topic") String topicName) {
		ModelAndView mv = new ModelAndView("problemlist");
		return null;
	}
}
