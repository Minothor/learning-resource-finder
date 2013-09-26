package learningresourcefinder.controller;

import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.service.CompetenceNodeService;

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
        ModelAndView mv = new ModelAndView("");
        Cycle cycle = null;
        
        if(cycleName != null && !cycleName.isEmpty())
        cycle = cycleRep.findByName(cycleName);
        
       // Call the competenceNodeService Method .... 
        
        return mv;
    }
    
}
