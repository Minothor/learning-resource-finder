package learningresourcefinder.controller;

import java.util.Map;
import java.util.TreeMap;

import learningresourcefinder.model.Problem;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.repository.ProblemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContributionController extends BaseController<Problem> {
	
	@Autowired ProblemRepository problemRepository;
	
	@RequestMapping("/contribution")
	public ModelAndView contribution() {		
		// Long[] = Number of problem on a topic, Number of fields null, Number of topic with competence null 
		Map<Topic, Long[]> mapProblem = new TreeMap<Topic, Long[]>();
		
		for(Topic topic : Resource.Topic.values()) {
			Long[] results = {problemRepository.countOpenProblemsForTopic(topic), problemRepository.countProblemOfFieldNull(topic), problemRepository.countProblemOfCompetenceNull(topic)};
			mapProblem.put(topic, results);
		}
				
		ModelAndView mv = new ModelAndView("contribution");
		mv.addObject("mapProblem", mapProblem);
		
		return mv;
	}
}
