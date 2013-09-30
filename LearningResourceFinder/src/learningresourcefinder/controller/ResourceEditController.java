package learningresourcefinder.controller;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.web.Slugify;
import learningresourcefinder.web.UrlUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResourceEditController extends BaseController<Resource> {

    @Autowired   ResourceRepository resourceRepository;
    @Autowired   UrlResourceRepository urlResourceRepository;
    @Autowired   IndexManagerService indexManager;
    
    

    @RequestMapping(value="/ajax/checkUrl",method=RequestMethod.POST)
    public @ResponseBody String urlSubmit(@RequestParam("url") String url) {
         String checkUrlOk = "";
         Resource resource = urlResourceRepository.getFirstResourceWithSimilarUrl(url);
         return resource == null ? "" : resource.getId() + "";
    }
    
    @RequestMapping("/ajax/resourceaddsubmit1")
//	public @ResponseBody String resourceAddSubmit() {
	public @ResponseBody MessageAndId resourceAddSubmit(@RequestParam("url") String url, @RequestParam("title") String title, @ModelAttribute SearchOptions searchoptions ) {
        SecurityContext.assertUserIsLoggedIn();
   
        Resource resource = new Resource();
       
        resource.setName(title);
        String slug = Slugify.slugify(resource.getName());
        resource.setSlug(slug);

        UrlResource urlResource = new UrlResource();
        urlResource.setName(title);
        urlResource.setUrl(url);
        urlResource.setResource(resource);
        resource.getUrlResources().add(urlResource);
        
        resource.setFormat(searchoptions.getFormat().get(0));
        

        urlResourceRepository.persist(urlResource);
        resourceRepository.persist(resource);
        
        indexManager.add(resource);
        
        return new MessageAndId(resource.getId(), "ressource ajoutée - <a href="+UrlUtil.getRelativeUrlToResourceDisplay(resource)+">Afficher</a>");
    }

    
    public static class MessageAndId {
        private String message;
        private long id;
        public MessageAndId(long id, String message) {
            this.message = message;
            this.id = id;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public long getId() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
        } 
    }
    
}
