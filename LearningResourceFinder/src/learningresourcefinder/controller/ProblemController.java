package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.Problem;
import learningresourcefinder.model.Discussion;
import learningresourcefinder.repository.DiscussionRepository;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.NotificationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProblemController extends BaseController<Problem> {
	
	@Autowired ResourceRepository resourceRepository;
	@Autowired ProblemRepository problemRepository;
	@Autowired DiscussionRepository discussionRepository;
	
   @RequestMapping(value="/problem")
    public @ResponseBody ModelAndView problemDisplay(@RequestParam("id") Long idProblem) {
        Problem problem = getRequiredEntity(idProblem);
        ModelAndView mv = new ModelAndView("problemdisplay", "problem", problem);
        mv.addObject("resource", problem.getResource());
        mv.addObject("problemDiscussions",problem.getProblemDiscussions());
        return mv;
    }
	
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
