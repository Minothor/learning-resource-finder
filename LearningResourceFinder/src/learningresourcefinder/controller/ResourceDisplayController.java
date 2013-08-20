package learningresourcefinder.controller;

import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ResourceDisplayController extends BaseController<Resource> {
	@Autowired ResourceRepository resourceRepository;

	@RequestMapping({"/resource/{id}/{slug}",
		             "/resource/{id}/", // SpringMVC needs us to explicitely specify that the {slug} is optional.	
                     "/resource/{id}" // SpringMVC needs us to explicitely specify that the "/" is optional.	
		})   
	public ModelAndView displayResouce(@PathVariable long id) {   
		Resource resource = getRequiredEntity(id);
		return new ModelAndView("resourcedisplay", "resource", resource); // JSP Name, Attribute Name, Attribute
	}
}
