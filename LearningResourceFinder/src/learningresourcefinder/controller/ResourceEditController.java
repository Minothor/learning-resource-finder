package learningresourcefinder.controller;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.web.Slugify;
import learningresourcefinder.web.UrlUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResourceEditController extends BaseController<Resource> {

    @Autowired   ResourceRepository resourceRepository;
    @Autowired   UrlResourceRepository urlResourceRepository;
    @Autowired   IndexManagerService indexManager;
    
    @RequestMapping("/ajax/resourceaddsubmit")
//	public @ResponseBody String resourceAddSubmit() {
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
        
        indexManager.add(resource);
        
       return "La ressource a été ajoutée avec succès."+"<a href="+UrlUtil.getRelativeUrlToResourceDisplay(resource)+">Afficher</a>";
    }

    @RequestMapping(value="/ajax/checkUrl",method=RequestMethod.POST)
	public @ResponseBody String urlSubmit(@RequestParam("url") String url) {
		 String checkUrlOk = "";
         Resource resource = urlResourceRepository.getFirstResourceWithSimilarUrl(url);
         return resource == null ? "" : resource.getId() + "";
	}
    
    
    // Called by JS to get the optional/expanding part of the form to add a resource in the modal dialog box. 
    @RequestMapping("/ajax/addresourceform")
	public String searchresource(Model model){
		model.addAttribute("formatEnumAllValues", SearchOptions.Format.values());
		model.addAttribute("languagesEnumAllValues", SearchOptions.Language.values());
		return "addresourceform";
	}
}
