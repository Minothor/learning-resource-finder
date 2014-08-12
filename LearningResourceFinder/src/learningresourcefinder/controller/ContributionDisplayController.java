package learningresourcefinder.controller;

import learningresourcefinder.model.User;
import learningresourcefinder.repository.ContributionRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.ContributionService;
import learningresourcefinder.service.LevelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class ContributionDisplayController extends BaseController<User>
	{
		@Autowired	ContributionService contributionService;  
		@Autowired ContributionRepository contributionRepository;
		@Autowired LevelService levelService;
		@Autowired UserRepository userRepository;
		
		

		
		@RequestMapping(value="/contributionlistdisplay", method=RequestMethod.GET)
	    public ModelAndView displayContributions(@RequestParam(value="username",required=false) String userName) {
	    	
	    	User user =(userName==null)? SecurityContext.getUser(): userRepository.getUserByUserName(userName);
	    	    	
	        ModelAndView mv= new ModelAndView ("contributiondisplay");
	        mv.addObject("contributions", contributionRepository.findAllByUser(user));
	        mv.addObject("user", user );
	        mv.addObject("contributionsPoints",contributionRepository.sumByUser(user));
	        mv.addObject("levelProgress",user.getUserProgressPoints());
	        
	        return mv;
	    }
		
		
		@RequestMapping(value="/recomputeContributionPoints", method=RequestMethod.GET)
		public String recomputeContributionPoints(@RequestParam(value="username",required=false) String userName) {
	    //
			User user =(userName==null)? SecurityContext.getUser(): userRepository.getUserByUserName(userName);
			levelService.computeContributionPointsForLevelFromContributions(user, contributionRepository.sumByUser(user));
					
			return "redirect:contributionlistdisplay?username=" + user.getUserName();
			
		}
	}
