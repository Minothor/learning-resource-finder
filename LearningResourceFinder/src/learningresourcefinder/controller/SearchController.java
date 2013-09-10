package learningresourcefinder.controller;

import learningresourcefinder.model.BaseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;

@Controller
public class SearchController extends BaseEntity {

	@RequestMapping("/searchresource")
	public ModelAndView searchGen (@RequestParam("")){
		ModelAndView mv = new ModelAndView("search");
			
		
		
		return mv;
	}

	@RequestMapping("/searchresource")
	public ModelAndView searchResource (@RequestParam("")){
		ModelAndView mv = new ModelAndView("search");
			
		
		
		return mv;
	}
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
