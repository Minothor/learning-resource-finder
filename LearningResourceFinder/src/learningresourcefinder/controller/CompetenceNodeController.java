package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.service.CompetenceNodeService;
import learningresourcefinder.util.CompetenceNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompetenceNodeController {
    
    @Autowired CycleRepository cycleRep;
    @Autowired CompetenceNodeService competenceNodeService;
    
    @RequestMapping(value="/competencebycycle", method = RequestMethod.GET)
    public ModelAndView competencebycycle(@RequestParam("name") String cycleName){

    	ModelAndView mv = new ModelAndView("CyclePageDisplay"); 
    	
        Cycle cycle = cycleRep.findByName(cycleName);
        CompetenceNode root = competenceNodeService.buildCompetenceNodeTree(cycle);
        List<List<CompetenceNode>> listToShow =  competenceNodeService.splitCompetenceNodesInColumns(root);
        mv.addObject("listColumns",listToShow);
        mv.addObject("Cycle",cycle);
        
        return mv;
    }
    
}
