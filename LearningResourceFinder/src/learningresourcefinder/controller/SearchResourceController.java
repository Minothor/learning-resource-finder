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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SearchResourceController extends BaseController<Resource> {
    
    public static final String MAP_SEARCH_OPTION_KEY = "mapSearchOptions";
    
	@Autowired private SearchService searchService;
	@Autowired private ResourceRepository resourceRepository;

    
	@RequestMapping("/searchresource")
	public ModelAndView searchResource(Model model, 
	        @RequestParam(value="searchphrase", required=false) String searchPhrase, 
	        @RequestParam(value="page", required=false) Integer page, 
            @RequestParam(value="noadvertizing", required=false) Boolean noAdvertising, 
	        @RequestParam(value="competenceid", required=false) Long competenceId,
	        HttpSession session,
	        @RequestParam(value="so", required=false) Long timeStamp){ 

        ///// We store the searchOptions in the session (via a map) because the parameters (advertising, languae, etc.) are not given
		///// in case the user clicks a page in the search result => we remember these search criteria from the session.
		SearchOptions searchOptions = getCreateOrGetSearchOptionsFromSession(session, timeStamp);

		// A. We store the given request parameters in the searchOptions object
        if (searchPhrase != null) {
            searchOptions.setSearchPhrase(searchPhrase);
        }
                
        if (competenceId != null) {
            searchOptions.setCompetence((Competence)getRequiredEntity(competenceId, Competence.class));
        }

        if (Boolean.TRUE.equals(noAdvertising)) {
            searchOptions.setAdvertising(false);
        } else {
            searchOptions.setAdvertising(null);  // We take all resources, with ad, without ad and those where we don't know.
        }

        if (page == null) {
            page=1;
        }

        // B. Perform the search
        List<SearchResult> searchResults = searchService.search(searchOptions.getSearchPhrase());
        List<Resource> entities = searchService.getFilteredResources(searchResults, page, searchOptions);
        
        
        // C. Prepare for the view
        ModelAndView mv = new ModelAndView("searchresource");
        mv.addObject("searchOptions", searchOptions);
        mv.addObject("natureEnumAllValues", SearchOptions.Nature.values());
        mv.addObject("platformsEnumAllValues", SearchOptions.Platform.values());
        mv.addObject("formatEnumAllValues", SearchOptions.Format.values());
        mv.addObject("languagesEnumAllValues", SearchOptions.Language.values());
        mv.addObject("timeStamp", timeStamp);
        
        mv.addObject("resourcelist", entities);

        int numberOfResourceFound = entities.size();
        mv.addObject("numberResource", numberOfResourceFound); // tried to use fn:length in EL, but it did not work -- Thomas S 2013/09
        
        return mv;
	}


    private synchronized SearchOptions getCreateOrGetSearchOptionsFromSession(HttpSession session, Long timeStamp) {
        // 1. Try to find the map of searchOptions for this user's session.
        // It's useful when the user has many tab opened with differents search options; we store one entry in the map per option tab.
        @SuppressWarnings("unchecked")
        Map<Long, SearchOptions> map = (Map<Long, SearchOptions>) session.getAttribute(MAP_SEARCH_OPTION_KEY);
        if (map == null) {
            map = new HashMap<Long, SearchOptions>();
            session.setAttribute(MAP_SEARCH_OPTION_KEY, map);
        }
        
	    // 2. from the map, try to get the SearchOption object for this request/tab.
		// we create timestamp which allows to keep the search options values in all pages while navigating to a different page
		if(timeStamp == null) { // First time we search with this tab.  
		    timeStamp = (new java.util.Date()).getTime();	
		}
		
		SearchOptions searchOptions = map.get(timeStamp);
		if (searchOptions == null) { // It's the first time we search for that tab
	        searchOptions = new SearchOptions();
	        map.put(timeStamp, searchOptions);
		}
        return searchOptions;
    }
}
