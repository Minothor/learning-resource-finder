package learningresourcefinder.controller;

import learningresourcefinder.mail.MailCategory;
import learningresourcefinder.mail.MailType;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.MailService;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.web.UrlUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController extends BaseController<User> {

    JavaMailSenderImpl javamail;
    @Autowired
    MailService mailService;
    @Autowired public UserRepository dao;
    @RequestMapping("/contact")
    public ModelAndView contactDisplay(){
        ModelAndView mv = new ModelAndView("contact");
        if (SecurityContext.getUser()!=null){
            mv.addObject("sender",SecurityContext.getUser().getMail());
        }
        return mv;
    }

    @SuppressWarnings("unused")
	@RequestMapping(value="/ajax/sendmail" , method=RequestMethod.POST)
    public @ResponseBody String sendMail(@RequestParam String sender, @RequestParam String subject, @RequestParam String content, WebRequest request){
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

           User user = SecurityContext.getUser();
           if (user != null) {  // This visitor is logged in as a user.
           	// We add a line with a link to that user, with it's full name.
           	content = "from : <a href='" + UrlUtil.getAbsoluteUrl("/user/" + user.getUserName()) + "'/>" 
           				+ user.getFullName() + "</a><br/><br/>"
           				+ content;
           }
           
           mailService.sendMail(mailService.ADMIN_MAIL, sender, subject,content, MailType.IMMEDIATE, MailCategory.CONTACT);
           NotificationUtil.addNotificationMessage("Votre message est bien envoyé");
           
           return "OK";  // Go to home page.
    }
    
    
    private ModelAndView prepareModelAndView(String sender,String subject,String content,String message, WebRequest request) {
    	NotificationUtil.addNotificationMessage(message);
        ModelAndView mv = new ModelAndView("contact");
        
        // Refill fields to prevent to user to retype them.
        mv.addObject("sender", sender);
        mv.addObject("subject", subject);
        mv.addObject("content", content);
        return mv;
    }
    
    public void sendMail(String replyTo, String to, String subject, String text) {

        SimpleMailMessage sms = new SimpleMailMessage();

        sms.setReplyTo(replyTo);
        sms.setTo(to);
        sms.setSubject(subject);
        sms.setText(text);

        javamail.send(sms);

        System.out.println("Message envoyé");

    }
    
}
