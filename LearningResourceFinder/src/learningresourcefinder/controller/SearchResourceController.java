package learningresourcefinder.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchResult;
import learningresourcefinder.service.ResourceListPagerService;
import learningresourcefinder.service.SearchService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SearchResourceController extends BaseController<Resource> {
    
    public static final String MAP_SEARCH_OPTION_KEY = "mapSearchOptions";
    
	@Autowired private SearchService searchService;
	@Autowired private ResourceRepository resourceRepository;
	@Autowired private ResourceListPagerService resourceListPager;

   @RequestMapping("/searchresourceform")  // Used when the user clicks the form button on searchresource.jsp
   public ModelAndView searchResource(
	            @ModelAttribute SearchOptions searchOptions,
	            @RequestParam(value="noadvertizing", required=false) Boolean noAdvertising,  // Cannot be injected through the ModelAttribute (because Boolean vs boolean?)
	            @RequestParam(value="competenceid", required=false) Long competenceId,
	            HttpSession session,
	            @RequestParam(value="so", required=false) Long timeStamp
	            ) {

       Map<Long, SearchOptions> map = createOrGetMapFromSession(session);
       if (timeStamp == null) {  // First time we search in this tab
           timeStamp = (new java.util.Date()).getTime();   
       }
       map.put(timeStamp, searchOptions);   // Maybe replaces a previous searchOptions
       
       
       if (Boolean.TRUE.equals(noAdvertising)) {
           searchOptions.setAdvertising(false);
       } else {
           searchOptions.setAdvertising(null);  // We take all resources, with ad, without ad and those where we don't know.
       }

       if (competenceId != null) {
           searchOptions.setCompetence((Competence)getRequiredEntity(competenceId, Competence.class));
       }

       return prepareModelAndView(timeStamp, searchOptions);
   }

   
   
	@RequestMapping("/searchresource")  // used as initial entry point from the header, or when the user clicks a page number, or clicks a competence
	public ModelAndView searchResource(Model model, 
	        @RequestParam(value="searchphrase", required=false) String searchPhrase, 
            @RequestParam(value="competenceid", required=false) Long competenceId,
	        HttpSession session,
	        @RequestParam(value="so", required=false) Long timeStamp) throws UnsupportedEncodingException{ 

	    if (searchPhrase != null)
	        searchPhrase = new String(searchPhrase.getBytes("ISO-8859-1"), "UTF-8"); // Replacement of wrong characters in the word.

	    
        ///// We store the searchOptions in the session (via a map) because the parameters (advertising, languae, etc.) are not given
		///// in case the user clicks a page in the search result => we remember these search criteria from the session.
		SearchOptions searchOptions = createOrGetSearchOptionsFromSession(session, timeStamp);

		// A. We store the given request parameters in the searchOptions object
        if (searchPhrase != null) {
            searchOptions.setSearchPhrase(searchPhrase);
        }
                
        if (competenceId != null) {
            searchOptions.setCompetence((Competence)getRequiredEntity(competenceId, Competence.class));
        }

        // C. Prepare for the view
        return prepareModelAndView(timeStamp, searchOptions);
	}

    private ModelAndView prepareModelAndView(Long timeStamp, SearchOptions searchOptions) {
        List<Resource> resourceList;
        if (StringUtils.isNotBlank(searchOptions.getSearchPhrase())) {
            List<SearchResult> searchResults = searchService.search(searchOptions.getSearchPhrase());
            resourceList = searchService.getFilteredResources1(searchResults, searchOptions);
        } else {  // User searches on a competence or on a rare filter combinations (i.e. "all the Arabic interactive resources")
            if (searchOptions.getCompetence() != null) { // No phrase but a competence => we first query for resources on the competence (we don't use Lucene)
                List<Long> resourceIdsForCompetence = resourceRepository.findIdsByCompetence(searchOptions.getCompetence());
                resourceList = searchService.getFilteredResources2(resourceIdsForCompetence, searchOptions);
            } else {  // Probably a rare option combination
                // TODO get all resources with that filter option
                resourceList = searchService.getFilteredResources3(searchOptions);
            }
        }
        
        
        ModelAndView mv = new ModelAndView("searchresource");
        
        // Special processing if more than xxx resources retrieved as we want to display a specific
        // maximum number of resources !
        if (resourceList.size() > ResourceListPagerService.NUMBER_OF_ROWS_FOR_SEARCH_TO_RETURN) {
            // Save complete list of resources (Ids) in a session's map and return key identifier 
            String KeyIdentifierListOfResources = resourceListPager.addListOfResources(resourceList, true);

            // Keep only xxx first resources
            resourceList = resourceList.subList(0, 
                    ResourceListPagerService.NUMBER_OF_ROWS_FOR_SEARCH_TO_RETURN > resourceList.size() ? 
                            resourceList.size() : ResourceListPagerService.NUMBER_OF_ROWS_FOR_SEARCH_TO_RETURN);
            
            // Pass unique key identifier to JSP; it will be used to retrieve more resources when scrolling !
            mv.addObject("tokenListOfResources", KeyIdentifierListOfResources);         
        }
        else {
            mv.addObject("tokenListOfResources", "0");
        }
        
        mv.addObject("searchOptions", searchOptions);
        mv.addObject("natureEnumAllValues", SearchOptions.Nature.values());
        mv.addObject("platformsEnumAllValues", SearchOptions.Platform.values());
        mv.addObject("formatEnumAllValues", SearchOptions.Format.values());
        mv.addObject("languagesEnumAllValues", SearchOptions.Language.values());
        mv.addObject("timeStamp", timeStamp);
        
        mv.addObject("resourcelist", resourceList);

        return mv;
    }


    private synchronized SearchOptions createOrGetSearchOptionsFromSession(HttpSession session, Long timeStamp) {
        // 1. Try to find the map of searchOptions for this user's session.
        // It's useful when the user has many tab opened with differents search options; we store one entry in the map per option tab.
        Map<Long, SearchOptions> map = createOrGetMapFromSession(session);
        
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

    private Map<Long, SearchOptions> createOrGetMapFromSession(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, SearchOptions> map = (Map<Long, SearchOptions>) session.getAttribute(MAP_SEARCH_OPTION_KEY);
        if (map == null) {
            map = new HashMap<Long, SearchOptions>();
            session.setAttribute(MAP_SEARCH_OPTION_KEY, map);
        }
        return map;
    }
}
