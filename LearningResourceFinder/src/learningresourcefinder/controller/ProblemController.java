package learningresourcefinder.controller;

import learningresourcefinder.model.Problem;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.util.NotificationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProblemController extends BaseController<Problem> {
	
	@Autowired ResourceRepository resourceRepository;
	@Autowired ProblemRepository problemRepository;
	
	@RequestMapping(value="/ajax/problemreport", method=RequestMethod.POST)
	public @ResponseBody String problemReport(@RequestParam("idresource") Long idResource, @RequestParam("title") String title, @RequestParam("description") String description) {
		Problem p = new Problem();
		p.setName(title);
		p.setDescription(description);
		p.setResource(resourceRepository.find(idResource));
		problemRepository.merge(p);
		NotificationUtil.addNotificationMessage("Le problème à bien été transmit. Merci de votre contribution !");
		return "success";
	}
}
