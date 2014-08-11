package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.Contribution;
import learningresourcefinder.repository.ContributionRepository;
import learningresourcefinder.security.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class ContributionDisplayController
	{
		@Autowired ContributionRepository contributionRepository;
		@RequestMapping("/contributionlistdisplay")
	    public ModelAndView displayContributions() {
	    	
	    	List<Contribution> contributions = contributionRepository.findAllByUser(SecurityContext.getUser());
	    	    	
	        ModelAndView model= new ModelAndView ("contributiondisplay","contributions",contributions);
	        
	        model.addObject("user",SecurityContext.getUser());
	        return model;
	    }
		
	}
