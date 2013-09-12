package learningresourcefinder.controller;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.web.Slugify;
import learningresourcefinder.web.UrlUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResourceEditController extends BaseController<Resource> {

    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    UrlResourceRepository urlResourceRepository;
    
    @RequestMapping("/ajax/resourceaddsubmit")
	public @ResponseBody String resourceAddSubmit(@RequestParam("url") String url, @RequestParam("title") String title, @RequestParam("description") String description) {
        SecurityContext.assertUserIsLoggedIn();

        Resource resource = new Resource();
        resource.setDescription(description);
        resource.setName(title);
        String slug = Slugify.slugify(resource.getName());
        resource.setSlug(slug);

        UrlResource urlResource = new UrlResource();
        urlResource.setName(title);
        urlResource.setUrl(url);
        urlResource.setResource(resource);
        resource.getUrlResources().add(urlResource);


        urlResourceRepository.persist(urlResource);
        resourceRepository.persist(resource);
        
       return "La ressource a été ajoutée avec succès."+"<a href="+UrlUtil.getRelativeUrlToResourceDisplay(resource)+">Afficher</a>";
    }

    @RequestMapping(value="/ajax/checkUrl",method=RequestMethod.POST)
	public @ResponseBody String urlSubmit(@RequestParam("url") String url) {
		 String checkUrlOk = "";
         Resource resource = urlResourceRepository.getFirstResourceWithSimilarUrl(url);
         return resource == null ? "" : resource.getId() + "";
	}
}
