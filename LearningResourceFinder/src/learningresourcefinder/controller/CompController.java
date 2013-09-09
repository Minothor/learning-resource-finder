package learningresourcefinder.controller;

import learningresourcefinder.model.BaseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompController extends BaseController<BaseEntity> {
    
   @RequestMapping ("/tree")
   public ModelAndView DisplayCompTree (){
       
       
       
       
       
       ModelAndView mv= new ModelAndView("competencestree");
    return mv; 
   }
   

}
