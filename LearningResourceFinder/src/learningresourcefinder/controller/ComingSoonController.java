package learningresourcefinder.controller;

import learningresourcefinder.model.ComingSoonMail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ComingSoonController extends BaseController<ComingSoonMail> {


    @RequestMapping("/")
    public String comingSoonRoot() {
      return "redirect:/comingsoon";   // If we don't redirect, we have sitemesh troubles (wrong decorator).
    }

    @RequestMapping("/comingsoon")
    public String comingSoon() {
        return "comingsoon";
    }

    @RequestMapping("/explication")
    public String explanation() {
        return "explanation";
    }

	@RequestMapping(value="/ajax/addMailOnTable")
	public @ResponseBody String  addMailOnTable( @RequestParam("mail") String addresemail){
	
		ComingSoonMail comingSoonMail = new ComingSoonMail();	
		comingSoonMail.setEmail(addresemail);
		em.persist(comingSoonMail);
		return "SUCCESS";  // We must return something (else the browser thinks it's an error case), but the value is not needed by our javascript code on the browser.
	}
}
