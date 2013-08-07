package learningresourcefinder.controller;

import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ResourceDisplayController extends BaseController<Resource> {
	@Autowired ResourceRepository resourceRepository;

	@RequestMapping("/resource")
	public ModelAndView displayResouce(@RequestParam("id") long id) {   
		Resource r = getRequiredEntity(id);
		return new ModelAndView("resourcedisplay", "resource", r); // JSP Name, Attribute Name, Attribute
	}
}
