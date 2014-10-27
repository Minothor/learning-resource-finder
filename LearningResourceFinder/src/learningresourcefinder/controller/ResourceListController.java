package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.exception.InvalidUrlException;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.repository.RatingRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.service.LevelService;
import learningresourcefinder.service.ResourceListPagerService;
import learningresourcefinder.web.ModelAndViewUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResourceListController extends BaseController<Resource> {
	
	@Autowired ResourceRepository resourceRepository;
	@Autowired UserRepository userRepository; //
	@Autowired RatingRepository ratingRepository; //
	@Autowired LevelService levelService;
	@Autowired CycleRepository cycleRepository;
	@Autowired ResourceListPagerService resourceListPager;
	
	@RequestMapping("/ressourcelist/{userName}")
	public ModelAndView resourceList(@PathVariable("userName") String userName) {
		
		User user = userRepository.getUserByUserName(userName);
        if (user == null) {
        	throw new InvalidUrlException("L'utilisateur ayant le pseudonyme (userName) '"+userName+"' est introuvable.");
        }
       
        ModelAndView mv = prepareModelAndView(resourceRepository.findAllResourceByUser(user));
        mv.addObject("titleFragment","Ressources ajoutées");

        mv.addObject("user", user);
        
		return mv; 
	
	}

   
	@RequestMapping("/problemresourcelist/{topic}")
	public ModelAndView problemresourcelist(@PathVariable("topic") String topicName) {		
		Topic topic = Resource.Topic.valueOf(topicName);
		ModelAndView mv = prepareModelAndView(resourceRepository.findAllResourceWhereProblemByTopic(topic));
		mv.addObject("topic", topic);
		mv.addObject("problemTitle","Liste des ressources contenant des problèmes.");
		return mv;
	}
	
	@RequestMapping("/fieldsnullresourcelist/{topic}")
	public ModelAndView fieldsnullresourcelist(@PathVariable("topic") String topicName) {
		Topic topic = Resource.Topic.valueOf(topicName);
		ModelAndView mv = prepareModelAndView(resourceRepository.findAllResourceWhereFieldsNullByTopic(topic));
	    mv.addObject("topic", topic);
		mv.addObject("problemTitle","Liste des ressources incomplètes.");
		return mv;
	}
	
	@RequestMapping("/notcompetencesresourcelist/{topic}") 
	public ModelAndView notcompetencesresourcelist(@PathVariable("topic") String topicName) {
		Topic topic = Resource.Topic.valueOf(topicName);
		ModelAndView mv = prepareModelAndView(resourceRepository.findAllResourceWhoNotCompetencesByTopic(topic));
	    mv.addObject("topic", topic);
		mv.addObject("problemTitle","Liste des ressources qui ne sont pas liées à une catégorie.");
		return mv;
	}
	
	@RequestMapping("/nochildrenvalidationresourcelist/{topic}") 
    public ModelAndView nochildrenvalidationresourcelist(@PathVariable("topic") String topicName) {
        Topic topic = Resource.Topic.valueOf(topicName);
        ModelAndView mv = prepareModelAndView(resourceRepository.findAllResourceWhoNoChildrenValidationByTopic(topic));

        mv.addObject("topic", topic);
        mv.addObject("problemTitle","Liste des ressources en attente de validation.");
        return mv;
    }
	
	
   @RequestMapping("/resourcelist")
   public ModelAndView resourceListByCycle(@RequestParam(value="cycleid" , required = false) Long cycleid ,@RequestParam(value="sort", required=false) String sort) {
       Cycle cycle = null ;
       List<Resource> resourceList = null;
       String titre = "";
       int maxResourcesNb = 100;
       if (cycleid == null){
           
           resourceList = resourceRepository.findLastResources(maxResourcesNb);
           titre = "les "+maxResourcesNb+" dernières ressources";
           
       } else if (sort!=null) {
           cycle = (Cycle)getRequiredEntity(cycleid, Cycle.class);
           
           if (sort.equals("popularity")) {
               resourceList = resourceRepository.findResourceByCycleAndPopularity(cycleid,sort);
               titre = "Ressources les plus populaires ";
           } else {
               resourceList = resourceRepository.findLastResourceByCycle(cycleid,maxResourcesNb);
               titre = "Les 100 dernières ressources ";
           }
           titre +=  " pour le cycle  "+cycle.getName();
       }
       
       ModelAndView mv = prepareModelAndView(resourceList);
       mv.addObject("titleFragment",titre);
       return mv;
   }
   
	private ModelAndView prepareModelAndView(List<Resource> listResource) {
	    ModelAndView mv = new ModelAndView("resourcelist");

	    // Special processing if more than xxx resources retrieved as we want to display a specific
	    // maximum number of resources !
	    if (listResource.size() > ResourceListPagerService.NUMBER_OF_ROWS_TO_RETURN) {
	        // Save complete list of resources (Ids) in a session's map and return key identifier 
	        String KeyIdentifierListOfResources = resourceListPager.addListOfResources(listResource, false);

	        // Keep only xxx first resources
	        listResource = listResource.subList(0, 
	                ResourceListPagerService.NUMBER_OF_ROWS_TO_RETURN > listResource.size() ? 
	                        listResource.size() : ResourceListPagerService.NUMBER_OF_ROWS_TO_RETURN);
	        
	        // Pass unique key identifier to JSP; it will be used to retrieve more resources when scrolling !
	        mv.addObject("tokenListOfResources", KeyIdentifierListOfResources);	        
	    }
	    else {
	        mv.addObject("tokenListOfResources", "0");
	    }
	        
	    
	    mv.addObject("resourceList", listResource);

	    ModelAndViewUtil.addRatingMapToModelAndView(mv, listResource);
	    
	    return mv;
	}

}
