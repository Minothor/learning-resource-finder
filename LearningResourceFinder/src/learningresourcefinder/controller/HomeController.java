package learningresourcefinder.controller;

import learningresourcefinder.repository.CycleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class HomeController {
    @Autowired CycleRepository cycleRepository;
    
    @RequestMapping(value={"/", "/home"})
    public String home() {
        return "home";
    }
    
}
