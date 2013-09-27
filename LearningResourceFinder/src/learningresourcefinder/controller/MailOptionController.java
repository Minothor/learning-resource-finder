package learningresourcefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import learningresourcefinder.mail.MailingDelayType;
import learningresourcefinder.model.Mail;
import learningresourcefinder.model.User;
import learningresourcefinder.service.MailService;

@Controller
public class MailOptionController extends BaseController<Mail>{

    @Autowired MailService mailService;
    
    @RequestMapping("/changemaildelay")
    public ModelAndView changeMailDelay(@RequestParam(value="newdelay") String newDelay,@RequestParam(value="iduser") Long idUser){
        ModelAndView mv = new ModelAndView("changemaildelay");
        if(isInEnum(newDelay,MailingDelayType.class)){
            User u = (User)getRequiredEntity(idUser, User.class);
            u.setMailingDelayType(MailingDelayType.valueOf(newDelay));
            mv.addObject("message"," Nouvelle fréquence d'envoi des mails sauvegardée : " + MailingDelayType.valueOf(newDelay).getName());
            return mv;
        } else {
            mv.addObject("message"," Cette fréquence n'existe pas ! : " + newDelay);
            return mv; 
        }
    }
    
    //check if a string value is contained in a particular Enum
    public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
          if(e.name().equals(value)) { return true; }
        }
        return false;
      }
    
}
