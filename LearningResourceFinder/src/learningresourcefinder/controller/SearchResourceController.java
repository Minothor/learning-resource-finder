package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.BaseEntity;
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
public class SearchResourceController {
	@Autowired private SearchService searchService;
	@Autowired private ResourceRepository resourceRepository;

	@RequestMapping("/searchresource")
	public String searchresource(String searchPhrase, Model model, @RequestParam("search") String searchResource, @RequestParam(value="page", required=false) Integer page){
		if (page == null) page=1;
		SearchOptions searchOptions = new SearchOptions();
		model.addAttribute("searchOptions", searchOptions);
		model.addAttribute("natureEnumAllValues", SearchOptions.Nature.values());
		model.addAttribute("platformsEnumAllValues", SearchOptions.Platform.values());
		model.addAttribute("formatEnumAllValues", SearchOptions.Format.values());
		model.addAttribute("languagesEnumAllValues", SearchOptions.Language.values());
		model.addAttribute("search", searchResource);
		
		List<SearchResult> searchResults = searchService.search(searchResource);
		List<BaseEntity> entities = resourceRepository.getFilteredEntities(searchResults, page, searchOptions);
		int numberOfResourceFound = searchResults.size();
		
		model.addAttribute("numberResource", numberOfResourceFound); // FIXME replace by EL
		
		model.addAttribute("resourcelist", entities);
		return "searchresource";
		
	}
	
	@RequestMapping("/searchresourcesubmit")
	public String searchresourcesubmit(@ModelAttribute SearchOptions searchOptions, Model model, @RequestParam("search") String searchResource, @RequestParam(value="page", required=false) Integer page){
		if (page == null) page=1;
		List<BaseEntity> entities = resourceRepository.getFilteredEntities(searchService.search(searchResource), page, searchOptions);
		model.addAttribute("resourcelist", entities);
		model.addAttribute("searchOptions", searchOptions);
		return "searchresource";
	}
	
	
}
