package learningresourcefinder.controller;

import java.util.ArrayList;
import java.util.List;

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

	@RequestMapping("/searchallresource")
	public ModelAndView getAllResource(@RequestParam("search") String searchResource){
		List<SearchResult> resultList = searchService.search(searchResource);
		List<Long> resultid = new ArrayList<Long>();
		
		for(SearchResult result : resultList){
			resultid.add(result.getId()); 
		}
		
		ModelAndView mv = new ModelAndView("search", "resourceList", resourceRepository.findResourcesByIdList(resultid));
		
		return mv;
	}
	
	@RequestMapping("/search")
	public ModelAndView getFirstFiveResource(@RequestParam("search") String searchResource){
		
		ModelAndView mv = new ModelAndView("search", "resourceList", searchService.getFirstResources(searchService.search(searchResource), 5));
		
		return mv;
	}
	
}
