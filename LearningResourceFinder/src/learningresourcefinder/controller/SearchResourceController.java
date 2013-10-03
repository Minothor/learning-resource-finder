package learningresourcefinder.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchResult;
import learningresourcefinder.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SearchResourceController extends BaseController<Resource> {
	@Autowired private SearchService searchService;
	@Autowired private ResourceRepository resourceRepository;

    Map<Long, SearchOptions> map = new HashMap<Long, SearchOptions>();
    
	@RequestMapping("/searchresource")
	public ModelAndView searchResource(Model model, 
	        @RequestParam(value="searchphrase", required=false) String searchPhrase, 
	        @RequestParam(value="page", required=false) Integer page, 
            @RequestParam(value="noadvertizing", required=false) Boolean noAdvertising,  // Handled separately from searchOptions because Spring MVC does not support Booleans (vs booleans) fields.
	        @RequestParam(value="competenceid", required=false) Long competenceId,
	        HttpSession session,
	        @RequestParam(value="so", required=false) Long timeStamp){ 
		if (page == null) page=1;

		SearchOptions searchOptions = new SearchOptions();
		searchOptions.setSearchPhrase(searchPhrase);
		
		// we create timestamp which allows to keep the search options values in all pages while navigating to a different page
		if(timeStamp == null){
		    timeStamp = (new java.util.Date()).getTime();	
		}
		
		// we iterate on the search options list stored in the session to get the right options
		// it's useful when the user has many tab opened with differents search options
		for(Map.Entry<Long, SearchOptions> entry : map.entrySet()){
		    if (entry.getKey().equals(timeStamp)) {
		        searchOptions = entry.getValue();                
		    }
		}
		return prepareModelAndView (searchOptions, noAdvertising, competenceId, page, session, timeStamp);
		
		
		
	}
//	
//	@RequestMapping("/searchresourcesubmit")  // FIXME Why does this method exist? John 2013-09-26
//	public ModelAndView searchResourceSubmit(@ModelAttribute SearchOptions searchOptions, 
//            @RequestParam(value="noadvertizing", required=false) Boolean noAdvertising,  // Handled separately from searchOptions because Spring MVC does not support Booleans (vs booleans) fields.
//			@RequestParam(value="competenceId", required=false) Long competenceId, 
//			@RequestParam(value="page", required=false) Integer page,
//			HttpSession session,
//			@RequestParam(value="so") Long timeStamp) {
//		
//		return prepareModelAndView(searchOptions, noAdvertising, competenceId, page, session, timeStamp);
//	}
//	
	
	
	private ModelAndView prepareModelAndView(SearchOptions searchOptions, Boolean noAdvertising, Long competenceId, Integer page, HttpSession session, Long timeStamp) {
		ModelAndView mv = new ModelAndView("searchresource");
		if (page == null) page=1;
		if (competenceId != null) {
			searchOptions.setCompetence((Competence)getRequiredEntity(competenceId, Competence.class));
		}

		if (Boolean.TRUE.equals(noAdvertising)) {
		    searchOptions.setAdvertising(false);
		} else {
		    searchOptions.setAdvertising(null);  // We take all resources, with ad, without ad and those where we don't know.
		}

		map.put(timeStamp, searchOptions);
		
		session.setAttribute("listSearchOptions", map);
		

		
		mv.addObject("searchOptions", searchOptions);
		mv.addObject("natureEnumAllValues", SearchOptions.Nature.values());
		mv.addObject("platformsEnumAllValues", SearchOptions.Platform.values());
		mv.addObject("formatEnumAllValues", SearchOptions.Format.values());
		mv.addObject("languagesEnumAllValues", SearchOptions.Language.values());
		mv.addObject("timeStamp", timeStamp);
		
		List<SearchResult> searchResults = searchService.search(searchOptions.getSearchPhrase());
		List<Resource> entities = searchService.getFilteredResources(searchResults, page, searchOptions);
		mv.addObject("resourcelist", entities);

		int numberOfResourceFound = entities.size();
		mv.addObject("numberResource", numberOfResourceFound); // tried to use fn:length in EL, but it did not work -- Thomas S 2013/09
		
		return mv;
	}
	
	
}
