package learningresourcefinder.controller;

import learningresourcefinder.model.ComingSoonMail;
import learningresourcefinder.repository.SendMailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ComingSoonController extends BaseController<ComingSoonMail> {

	@Autowired SendMailRepository sendMailRepository;
	
	@RequestMapping(value="/ajax/addMailOnTable", method = RequestMethod.POST)
	public @ResponseBody String  addMailOnTable( @RequestParam("mail") String addresemail){
	
		ComingSoonMail comingSoonMail = new ComingSoonMail();	
		comingSoonMail.setEmail(addresemail);
		em.persist(comingSoonMail);
		em.getTransaction().commit();
		return "OK";  // We must return something (else the browser thinks it's an error case), but the value is not needed by our javascript code on the browser.
	}
}
