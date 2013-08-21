package learningresourcefinder.controller;

import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.security.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ResourceDisplayController extends BaseController<Resource> {
    @Autowired ResourceRepository resourceRepository;

    @RequestMapping({"/resource/{shortId}/{slug}",
        "/resource/{shortId}/", // SpringMVC needs us to explicitely specify that the {slug} is optional.   
        "/resource/{shortId}" // SpringMVC needs us to explicitely specify that the "/" is optional.    
    })   
    public ModelAndView displayResourceByShortId(@PathVariable String shortId) {   
        Resource resource = getRequiredEntityByShortId(shortId);
        
        ModelAndView mv = new ModelAndView("resourcedisplay", "resource", resource);
    	mv.addObject("canEdit", (SecurityContext.canCurrentUserEditResource(resource)));
		return mv;
    }
}
