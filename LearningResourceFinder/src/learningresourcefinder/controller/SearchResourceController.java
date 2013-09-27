package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.BaseEntity;
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


@Controller
public class SearchResourceController extends BaseController<Resource> {
	@Autowired private SearchService searchService;
	@Autowired private ResourceRepository resourceRepository;

	@RequestMapping("/searchresource")
	public String searchResource(Model model, @RequestParam("searchphrase") String searchPhrase, @RequestParam(value="page", required=false) Integer page, @RequestParam(value="competenceid", required=false) Long idCompetence ){
		if (page == null) page=1;
		SearchOptions searchOptions = new SearchOptions();
		model.addAttribute("searchOptions", searchOptions);
		model.addAttribute("natureEnumAllValues", SearchOptions.Nature.values());
		model.addAttribute("platformsEnumAllValues", SearchOptions.Platform.values());
		model.addAttribute("formatEnumAllValues", SearchOptions.Format.values());
		model.addAttribute("languagesEnumAllValues", SearchOptions.Language.values());

		searchOptions.setSearchPhrase(searchPhrase);
		if (idCompetence != null) {
			searchOptions.setCompetence((Competence)getRequiredEntity(idCompetence, Competence.class));
		}
		
		List<SearchResult> searchResults = searchService.search(searchPhrase);
		List<Resource> entities = searchService.getFilteredResources(searchResults, page, searchOptions);
		model.addAttribute("resourcelist", entities);

		int numberOfResourceFound = searchResults.size();
		model.addAttribute("numberResource", numberOfResourceFound); // tried to use fn:length in EL, but it did not work -- Thomas S 2013/09
		
		return "searchresource";
		
	}
	
	@RequestMapping("/searchresourcesubmit")  // FIXME Why does this method exist? John 2013-09-26
	public String searchResourceSubmit(@ModelAttribute SearchOptions searchOptions, 
			@RequestParam(value="competenceId", required=false) Long competenceId, Model model, @RequestParam(value="page", required=false) Integer page) {
		if (competenceId != null) {
			searchOptions.setCompetence((Competence)getRequiredEntity(competenceId, Competence.class));
		}
		if (page == null) page=1;
		List<Resource> entities = searchService.getFilteredResources(searchService.search(searchOptions.getSearchPhrase()), page, searchOptions);
		model.addAttribute("resourcelist", entities);
		model.addAttribute("searchOptions", searchOptions);
		return "searchresource";
	}
	
	
}
