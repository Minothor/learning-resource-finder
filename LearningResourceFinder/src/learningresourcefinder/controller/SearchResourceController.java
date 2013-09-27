package learningresourcefinder.controller;

import java.util.List;

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

	@RequestMapping("/searchresource")
	public ModelAndView searchResource(Model model, @RequestParam("searchphrase") String searchPhrase, @RequestParam(value="page", required=false) Integer page, @RequestParam(value="competenceid", required=false) Long competenceId ){
		if (page == null) page=1;

		SearchOptions searchOptions = new SearchOptions();
		searchOptions.setSearchPhrase(searchPhrase);
		

		return prepareModelAndView (searchOptions, competenceId, page);
		
//		searchOptions.setSearchPhrase(searchPhrase);
//		if (idCompetence != null) {
//			searchOptions.setCompetence((Competence)getRequiredEntity(idCompetence, Competence.class));
//		}
//		
//		List<SearchResult> searchResults = searchService.search(searchPhrase);
//		List<Resource> entities = searchService.getFilteredResources(searchResults, page, searchOptions);
//		model.addAttribute("resourcelist", entities);
//
//		int numberOfResourceFound = searchResults.size();
//		model.addAttribute("numberResource", numberOfResourceFound); // tried to use fn:length in EL, but it did not work -- Thomas S 2013/09
		
		
	}
	
	@RequestMapping("/searchresourcesubmit")  // FIXME Why does this method exist? John 2013-09-26
	public ModelAndView searchResourceSubmit(@ModelAttribute SearchOptions searchOptions, 
			@RequestParam(value="competenceId", required=false) Long competenceId, 
			@RequestParam(value="page", required=false) Integer page) {
		
		return prepareModelAndView(searchOptions, competenceId, page);
	}
	
	private ModelAndView prepareModelAndView(SearchOptions searchOptions, Long competenceId, Integer page) {
		ModelAndView mv = new ModelAndView("searchresource");
		if (page == null) page=1;
		if (competenceId != null) {
			searchOptions.setCompetence((Competence)getRequiredEntity(competenceId, Competence.class));
		}

		mv.addObject("searchOptions", searchOptions);
		mv.addObject("natureEnumAllValues", SearchOptions.Nature.values());
		mv.addObject("platformsEnumAllValues", SearchOptions.Platform.values());
		mv.addObject("formatEnumAllValues", SearchOptions.Format.values());
		mv.addObject("languagesEnumAllValues", SearchOptions.Language.values());

		List<SearchResult> searchResults = searchService.search(searchOptions.getSearchPhrase());
		List<Resource> entities = searchService.getFilteredResources(searchResults, page, searchOptions);
		mv.addObject("resourcelist", entities);

		int numberOfResourceFound = searchResults.size();
		mv.addObject("numberResource", numberOfResourceFound); // tried to use fn:length in EL, but it did not work -- Thomas S 2013/09
		
		return mv;
	}
	
	
}
