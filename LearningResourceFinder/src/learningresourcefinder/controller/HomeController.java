package learningresourcefinder.controller;

import java.security.Security;

import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.User;
import learningresourcefinder.security.SecurityContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController extends BaseController<User>{
 
    @RequestMapping(value={"/", "/home"})
    private ModelAndView prepareModelAndView() {
        User u = SecurityContext.getUser();
        return new ModelAndView("home", "user", u);
    }
}
