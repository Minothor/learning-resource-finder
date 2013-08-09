package learningresourcefinder.controller;


import learningresourcefinder.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController extends BaseController<User> {


    @RequestMapping(value="/helloworld")
    public String helloWorld() {
        return "helloworld";
    }
   
    @RequestMapping("/helloworldmv")
    public ModelAndView helloWorldMv() {
    	return new ModelAndView("helloworld");
    }

//    @RequestMapping("/helloworldmvr")
//    public ModelAndView() helloWorldMvr{
//    	return new ModelAndView("redirect:google.be");
//    }

}
