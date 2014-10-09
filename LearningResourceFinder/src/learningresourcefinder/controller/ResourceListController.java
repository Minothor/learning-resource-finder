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
	@Autowired CycleRepository cycleRepository ;

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
       
       if (cycleid == null){
           int maxResourcesNb = 100;
           resourceList = resourceRepository.findLastResources(maxResourcesNb);
           titre = "les "+maxResourcesNb+" dernières ressources";
           
       } else if (sort!=null) {
           cycle = (Cycle)getRequiredEntity(cycleid, Cycle.class);
           resourceList = resourceRepository.findResourceByCycleAndPopularity(cycleid,sort);
           if (sort.equals("popularity")) {
               titre = "Ressources les plus populaires ";
           } else {
               titre = "Ressources classées par date ";
           }
           titre +=  "pour le cycle  "+cycle.getName();
       } 
       
       ModelAndView mv = prepareModelAndView(resourceList);
       mv.addObject("titleFragment",titre);
       return mv;
   }
   
	private ModelAndView prepareModelAndView(List<Resource> listResource) {
	    ModelAndView mv = new ModelAndView("resourcelist");

	    mv.addObject("resourceList", listResource);

	    ModelAndViewUtil.addRatingMapToModelAndView(mv, listResource);
	    
	    return mv;
	}

}
