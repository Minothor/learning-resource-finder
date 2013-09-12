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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchSummaryController extends BaseController<Resource> {

	@Autowired SearchService searchService;
	@Autowired IndexManagerService indexService;
	@Autowired ResourceRepository resourceRepository;

	@RequestMapping("/searchresource")
	public ModelAndView getAllResource(){

		List<SearchResult> searchListe = searchService.search("mathématique");
		List<Resource> resourceList = new ArrayList<Resource>();

		for(SearchResult result : searchListe){
			if(result.getEntityClassName().equals("Resource")){
				Resource resource = resourceRepository.find(result.getId());
				resourceList.add(resource);
			} 
			
		}
		
		ModelAndView mv = new ModelAndView("search", "resourceList", resourceList);
		
		return mv;
	}
	
}
