package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.Resource;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class SearchResourceController {
	@Autowired private SearchService searchService;

	@RequestMapping("/searchresource")
	public String searchresource(String searchPhrase, Model model, @RequestParam("search") String searchResource){
		SearchOptions searchoptions = new SearchOptions();
		model.addAttribute("searchoptions", searchoptions);
		model.addAttribute("natureEnumAllValues", SearchOptions.Nature.values());
		model.addAttribute("platformsEnumAllValues", SearchOptions.Platforms.values());
		model.addAttribute("formatEnumAllValues", SearchOptions.Formats.values());
		model.addAttribute("languagesEnumAllValues", SearchOptions.Languages.values());
		model.addAttribute("search", "math"); // change math to searchResource
		return "searchresource";
		
	}
	
	@RequestMapping("/searchresourcesubmit")
	public String searchresourcesubmit(@ModelAttribute SearchOptions searchOptions, Model model, @RequestParam("search") String searchResource){
		model.addAttribute("resourcelist", searchService.getFirstEntities(searchService.search(searchResource), 5, Resource.class));
		model.addAttribute("searchOptions", searchOptions);
		//ModelAndView mv = new ModelAndView("searchresource", "searchOptions", searchOptions);
		return "searchresource";
	}
	
	
}
