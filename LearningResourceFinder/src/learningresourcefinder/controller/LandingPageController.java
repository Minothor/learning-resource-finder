package learningresourcefinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LandingPageController {
    
    
   @RequestMapping(value = "/exercices-de-neerlandais")
   public ModelAndView exerciceDeNeerlandais() {
        ModelAndView mv = new ModelAndView("landingpage");
        mv.addObject("introtextjsp", "introexerciceneerlandais.jsp");
        return mv;
   }

   
   @RequestMapping(value = "/exercices-de-math")
   public ModelAndView exerciceDeMath() {
        ModelAndView mv = new ModelAndView("landingpage");
        mv.addObject("introtextjsp", "introexercicemath.jsp");
        return mv;
   }
   
   
 }


