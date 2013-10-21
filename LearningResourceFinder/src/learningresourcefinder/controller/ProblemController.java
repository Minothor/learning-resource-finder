package learningresourcefinder.controller;

import java.util.HashMap;
import java.util.Map;

import learningresourcefinder.model.Problem;
import learningresourcefinder.model.Discussion;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.DiscussionRepository;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.LevelService;
import learningresourcefinder.util.Action;
import learningresourcefinder.util.DateUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.web.UrlUtil;

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
	@Autowired LevelService levelService;
   
	@RequestMapping(value="/problem")
    public @ResponseBody ModelAndView problemDisplay(@RequestParam("id") Long idProblem) {
	    User user = SecurityContext.getUser();
        Problem problem = getRequiredEntity(idProblem);
        Map<Long, String> discussionDate = new HashMap<Long, String>();
        
        ModelAndView mv = new ModelAndView("problemdisplay", "problem", problem);
        mv.addObject("user",SecurityContext.getUser());
        mv.addObject("resource", problem.getResource());
        mv.addObject("problemDiscussions",problem.getProblemDiscussions());
        for(Discussion discussion: problem.getProblemDiscussions()){
            discussionDate.put(discussion.getId(), DateUtil.formatIntervalFromToNowFR(discussion.getCreatedOn()));
        }
        mv.addObject("discussionDate", discussionDate);
        mv.addObject("canCloseProblem", levelService.canDoAction(user, Action.CLOSE_PROBLEM));
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
	
    @RequestMapping(value="/addDiscussion", method=RequestMethod.POST)
    public ModelAndView addDiscussion(@RequestParam("idProblem")long idProblem,@RequestParam("textDiscussion")String message) {
        Problem problem = (Problem) getRequiredEntity(idProblem);
        Discussion d = new Discussion(message);
        d.setProblem(problem);
        discussionRepository.persist(d);
        return new ModelAndView("redirect:/problem?id="+ idProblem+"#anchorResponse");  
    }
    
    @RequestMapping(value="/closeproblem")
    public ModelAndView addDiscussion(@RequestParam("id")long idProblem) {
        Problem problem = (Problem) getRequiredDetachedEntity(idProblem);
        
        problem.setResolved(true);
        problemRepository.merge(problem);
        return new ModelAndView("redirect:/"+UrlUtil.getRelativeUrlToResourceDisplay(problem.getResource())); //FIXME rediriger par la suite vers la liste des problèmes ou l'historique des problèmes remontés par le user  
    }
}
