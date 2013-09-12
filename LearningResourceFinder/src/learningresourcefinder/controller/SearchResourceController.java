package learningresourcefinder.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class SearchResourceController {
	List<String> languages = new ArrayList();
	List<String> formats = new ArrayList();
	boolean advertising;
	Integer maxDuration;

	@RequestMapping(value="/filter")
	public String searchFilter(){

		return "OK";
	}
}
