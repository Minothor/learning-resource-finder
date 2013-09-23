package learningresourcefinder.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Competence;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.search.SearchResult;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchSummaryController extends BaseController<Resource> {

	@Autowired private SearchService searchService;
	@Autowired private IndexManagerService indexService;
	@Autowired private ResourceRepository resourceRepository;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/searchallresource")
	public ModelAndView searchAllResource(@RequestParam("search") String searchResource){
		List<SearchResult> resultList = searchService.search(searchResource);
		List<Resource> resources = (List) searchService.getFirstEntities(resultList, 300, Resource.class);
		
		ModelAndView mv = new ModelAndView("search", "resourceList", resources);
		
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/search")
	public ModelAndView getFirstFiveResource(@RequestParam("search") String searchResource){
		List<SearchResult> listOfResult = searchService.search(searchResource);
		List<Competence> listOfCompetence = (List)searchService.getFirstEntities(listOfResult, 50, Competence.class);

//		List<Competence> list = new ArrayList<>();
//
//		list.add((Competence) listOfCompetence.get(0));
//		for(BaseEntity result : listOfCompetence){
//			if(result instanceof Competence){
//				list.add( ((Competence) result).getParent() );
//			}
//		}
//		Collections.reverse(list);
	
		ModelAndView mv = new ModelAndView("search");
		mv.addObject("resourceList", searchService.getFirstEntities(listOfResult, 5, Resource.class));
		mv.addObject("playlistList", searchService.getFirstEntities(listOfResult, 5, PlayList.class));
		mv.addObject("competenceList", listOfCompetence);
		mv.addObject("searchValue", searchResource);
		return mv;
	}
	
}
