package learningresourcefinder.controller;

import learningresourcefinder.exception.UserNotFoundException;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.service.LoginService;
import learningresourcefinder.service.UserService;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;
import learningresourcefinder.web.UrlUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ResendPasswordController extends BaseController<User>{
    
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired LoginService loginService;
    
    @RequestMapping("/resendpassword")
    public String resendPassword(){
        return "resendpassword";
    }
    
    @RequestMapping("/resendpasswordsubmit")
    public ModelAndView resendPasswordSubmit(@RequestParam("identifier") String identifier,WebRequest request){

        ModelAndView mv = new ModelAndView("redirect:resendpassword");  
        if(identifier == null || StringUtils.isBlank(identifier)){       
            mv.addObject("mailerrormessage", "Veuillez entrer un adresse email valide."); 
            return mv;
        }
        User user;
        try {
            
            user = loginService.identifyUserByEMailOrName(identifier);
            
        } catch (UserNotFoundException e) {
            mv.addObject("mailerrormessage", "L'e-mail que vous avez entrée ne correspond à aucun utilisateur enregistré sur "+UrlUtil.getWebSiteName());  
            return mv;
        }
    
            userService.generateNewPasswordForUserAndSendEmail(user);
            NotificationUtil.addNotificationMessage("Votre nouveau mot de passe viens de vous être envoyé sur votre adresse e-mail à "+user.getMail(),Status.INFO);
            return new ModelAndView("redirect:home");
        

    }
    
    

}
