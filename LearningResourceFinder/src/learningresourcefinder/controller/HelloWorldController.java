package learningresourcefinder.controller;


import javax.servlet.http.HttpServletRequest;

import learningresourcefinder.exception.InvalidPasswordException;
import learningresourcefinder.exception.UserLockedException;
import learningresourcefinder.exception.UserNotFoundException;
import learningresourcefinder.exception.UserNotValidatedException;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.AccountConnectedType;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.service.LoginService;
import learningresourcefinder.service.LoginService.WaitDelayNotReachedException;
import learningresourcefinder.util.DateUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.web.Cookies;
import learningresourcefinder.web.UrlUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController extends BaseController<User> {


    @RequestMapping(value="/helloworld", method=RequestMethod.GET)
    public String signin(HttpRequest request) {
        return "helloworld";
    }
   
}
