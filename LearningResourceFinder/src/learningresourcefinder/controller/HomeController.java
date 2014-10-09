package learningresourcefinder.controller;

import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




@Controller
public class HomeController {
    @Autowired CycleRepository cycleRepository;
    @Autowired ResourceRepository resourceRepository;
    
    @RequestMapping(value={"/", "/home"})
    public ModelAndView home() {
        
        return new ModelAndView("home", "nbResouces", resourceRepository.countResources());
    }
    
  
}
