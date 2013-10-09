package learningresourcefinder.controller;

import learningresourcefinder.exception.InvalidUrlException;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.web.Slugify;
import learningresourcefinder.web.UrlUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public @ResponseBody MessageAndId resourceAddSubmit(@RequestParam(value="url", required=false) String url, 
	        @RequestParam(value="title",required=false) String title,
	        @RequestParam(value="format",required=true) Format format, 
	        @RequestParam(value="platform",required=true) Platform platform, 
	        @RequestParam(value="topic",required=true) Topic topic) {
        SecurityContext.assertUserIsLoggedIn();
   
        Resource resource = new Resource();
       
        resource.setName(title);
        String slug = Slugify.slugify(resource.getName());
        resource.setSlug(slug);
        resource.setFormat(format);
        resource.setPlatform(platform);
        resource.setTopic(topic);
        
        UrlResource urlResource = new UrlResource();
        urlResource.setName(title);
        urlResource.setUrl(url);
        urlResource.setResource(resource);
        resource.getUrlResources().add(urlResource);
        
        //resource.setFormat(searchoptions.getFormat().get(0));
        

        urlResourceRepository.persist(urlResource);
        resourceRepository.persist(resource);
        
        indexManager.add(resource);
        
        return new MessageAndId(resource.getId(), "ressource ajoutée - <a href="+UrlUtil.getRelativeUrlToResourceDisplay(resource)+">Afficher</a>");
    }
    
    @RequestMapping("/ajax/resourceaddsubmit2")
    public @ResponseBody MessageAndId resourceAddSubmit2(@RequestParam(value="description",required=false) String description,
            @RequestParam(value="idresource",required=false) Long id,
            @RequestParam(value="language",required=false) Language language,
            @RequestParam(value="advertising",required=false) Boolean advertising,
            @RequestParam(value="maxDuration",required=false) Integer duration,
            @RequestParam(value="nature", required=false) Nature nature){
        if (id == null) {
            throw new InvalidUrlException("Missing resource id.");
        }
        Resource resource=getRequiredEntity(id);
        resource.setLanguage(language);
        resource.setDescription(description);
        resource.setAdvertising(advertising);
        resource.setDuration(duration); 
        resource.setNature(nature);
        resourceRepository.merge(resource); 
        indexManager.update(resource);
        
        return new MessageAndId(id,"La ressource a bien été ajoutée");
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
