package learningresourcefinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController{
 
    @RequestMapping("/")
    public String showHomePage(){
        return "home"; // JSP (this controller is not doing anything useful but giving access to the JSP).
    } 
       
}
