package learningresourcefinder.controller;

import learningresourcefinder.mail.MailCategory;
import learningresourcefinder.mail.MailType;
import learningresourcefinder.model.User;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.MailService;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;
import learningresourcefinder.web.UrlUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.restfb.util.StringUtils;

@Controller
public class ContactController extends BaseController<User> {

    @Autowired MailService mailService;
     
    @RequestMapping("/contact")
    public ModelAndView contactDisplay(){
        String sender = SecurityContext.getUser()!=null ? SecurityContext.getUser().getMail() : "";
        return prepareModelAndView(sender, "", "");
    }

    
    @SuppressWarnings("unused")
    @RequestMapping(value="/contactsubmit")
    public ModelAndView contactSubmit(@RequestParam String sender, @RequestParam String subject, @RequestParam String content){
           if (StringUtils.isBlank(sender)) {
               NotificationUtil.addNotificationMessage("Veuillez spécifier une adresse électronique où nous pourions vous répondre.", Status.ERROR);
               return prepareModelAndView(sender, subject, content);
           }
           content = "sender: "+sender +"<br/>";
        
           content += getFromLoggedInUserString(content);
           
           mailService.sendMail(mailService.ADMIN_MAIL, sender, subject,content, MailType.IMMEDIATE, MailCategory.CONTACT);
           NotificationUtil.addNotificationMessage("Votre message est bien envoyé");
           
           return new ModelAndView("redirect:/");  // Go to home page.
    }


    private String getFromLoggedInUserString(String content) {
        User user = SecurityContext.getUser();
           if (user != null) {  // This visitor is logged in as a user.
            // We add a line with a link to that user, with it's full name.
            content = "from : <a href='" + UrlUtil.getAbsoluteUrl("/user/" + user.getUserName()) + "'/>" 
                        + user.getFullName() + "</a><br/><br/>"
                        + content;
           }
        return content;
    }
    
    
    // Used by form fragment in support.jsp
    @SuppressWarnings("unused")
	@RequestMapping(value="/ajax/sendmail")
    public @ResponseBody String sendMail(@RequestParam String sender, @RequestParam String subject, @RequestParam String content){
    	   ModelAndView mv = new ModelAndView("contact");
           
           if (content.toString().equals("")) {
               return "FAIL";
           }
           
           if (subject.toString().equals("")) {
        	   return "FAIL";
           }
           
           if (sender.toString().equals("")) {
        	   return "FAIL";
           }

           content = getFromLoggedInUserString(content);
           
           mailService.sendMail(mailService.ADMIN_MAIL, sender, subject,content, MailType.IMMEDIATE, MailCategory.CONTACT);
           NotificationUtil.addNotificationMessage("Votre message est bien envoyé");
           
           return "SUCCESS";  
    }
    
    
    private ModelAndView prepareModelAndView(String sender, String subject, String content) {
        ModelAndView mv = new ModelAndView("contact");
        
        // Refill fields to prevent to user to retype them.
        mv.addObject("sender", sender);
        mv.addObject("subject", subject);
        mv.addObject("content", content);
        return mv;
    }
    
}
