package learningresourcefinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class HomeController {
    @Autowired CycleRepository cycleRepository;
    
    @RequestMapping(value={"/", "/home"})
    private ModelAndView home(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("home"); 
        List<Cycle> list=cycleRepository.findAllCycles();
       request.getSession().setAttribute("cyclelist",list);
         
        return mv;
    }
    
}
