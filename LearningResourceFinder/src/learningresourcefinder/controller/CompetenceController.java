package learningresourcefinder.controller;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Competence;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompetenceController extends BaseController<Competence> {
    
   @RequestMapping ("/tree")
   public ModelAndView DisplayCompTree (){
       
              
       ModelAndView mv= new ModelAndView("competencetree");
    return mv; 
   }
   

}
