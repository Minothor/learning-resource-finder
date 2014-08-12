package learningresourcefinder.controller;

import learningresourcefinder.model.User;
import learningresourcefinder.service.LoginService;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;
import learningresourcefinder.web.UrlUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class LogoutController extends BaseController<User> {
    
    @Autowired LoginService loginService;

    @RequestMapping("/logout")
    public String logout(WebRequest request) {
  
         loginService.logout();
         NotificationUtil.addNotificationMessage("Vous êtes à present deconnecté de "+UrlUtil.getWebSiteName(),Status.INFO);
        return "redirect:/";
    }
    @RequestMapping("/ajax/logout")
    public ResponseEntity<String> logoutAjax() {
         loginService.logout();
        return new ResponseEntity<String>("loged out", HttpStatus.OK);
    } 
}
