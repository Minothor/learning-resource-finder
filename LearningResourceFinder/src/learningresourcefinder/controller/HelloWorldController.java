package learningresourcefinder.controller;


import learningresourcefinder.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController extends BaseController<User> {


    @RequestMapping(value="/helloworld")
    public String helloWorld() {
        return "helloworld";
    }
   
}
