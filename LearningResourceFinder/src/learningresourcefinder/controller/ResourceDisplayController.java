package learningresourcefinder.controller;

import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.security.Privilege;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.web.Slugify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ResourceDisplayController extends BaseController<Resource> {
    @Autowired ResourceRepository resourceRepository;
    @Autowired IndexManagerService indexManager;

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
    
    @RequestMapping("/ajax/resourceeditfieldsubmit")
   	public @ResponseBody String resourceAddSubmit(@RequestParam("pk") Long id, @RequestParam("value") String value, @RequestParam ("name") String fieldName) {
    	
           Resource resource = getRequiredEntity(id);
           SecurityContext.assertCurrentUserMayEditThisResource(resource);
           
           if (fieldName.equals("title")){
        	   resource.setName(value);
               String slug = Slugify.slugify(resource.getName());
               resource.setSlug(slug);
           }
           else if(fieldName.equals("description")){
        	   resource.setDescription(value);
           }
           
           resourceRepository.merge(resource);
           
           indexManager.update(resource);
           
           return "success";
       }

    
}
