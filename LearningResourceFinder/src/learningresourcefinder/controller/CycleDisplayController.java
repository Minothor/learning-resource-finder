package learningresourcefinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import learningresourcefinder.model.Cycle;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.service.CompetenceNodeService;
import learningresourcefinder.util.CompetenceNode;
@Controller
public class CycleDisplayController extends BaseController<Cycle> {
	
    @Autowired CycleRepository cycleRepositoryy;
    @Autowired CompetenceNodeService competenceNodeService;
    @Autowired ResourceRepository resourceRepository; 
    
    @RequestMapping({"/cycle/{id}/{slug}",
        "/cycle/{id}/", // SpringMVC needs us to explicitely specify that the {slug} is optional.   
        "/cycle/{id}"   // SpringMVC needs us to explicitely specify that the "/" is optional.    
    }) 
    public ModelAndView displayCycle(@PathVariable long id) {
    	ModelAndView mv = new ModelAndView("cycledisplay");
        Cycle cycle=(Cycle)getRequiredEntity(id, Cycle.class);
        CompetenceNode root = competenceNodeService.buildCompetenceNodeTree(cycle);
        List<List<CompetenceNode>> listToShow =  competenceNodeService.splitCompetenceNodesInColumns(root);
        mv.addObject("listColumns",listToShow);
        mv.addObject("cycle",cycle);   
        return mv;
    }
}
