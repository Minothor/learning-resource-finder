package learningresourcefinder.controller;

import java.util.Set;

import learningresourcefinder.exception.InvalidUrlException;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.model.Resource.ValidationStatus;
import learningresourcefinder.model.UrlGeneric;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlGenericRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.ContributionService;
import learningresourcefinder.service.ImportService;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.service.LevelService;
import learningresourcefinder.util.Action;
import learningresourcefinder.util.Logger;
import learningresourcefinder.web.Slugify;
import learningresourcefinder.web.UrlUtil;

import org.apache.commons.logging.Log;
import org.json.simple.JSONObject;
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
    @Autowired   LevelService levelService ;
    @Autowired 	 ContributionService contributionService; 
    @Autowired   ImportService importService;
    @Autowired	 UrlGenericRepository urlgenericrepository;
    @Logger Log log;

    
    @RequestMapping(value="/ajax/increment")
    public @ResponseBody Long incrementResource(@RequestParam("idResource") Long idResource) {
    	Resource resource = resourceRepository.find(idResource);
    	resource.incViewCount();
    	return resource.getViewCount();
    }
       
    
    @RequestMapping(value="/ajax/checkUrl",method=RequestMethod.POST)
    public @ResponseBody JSONObject urlSubmit(@RequestParam("url") String url) {
    	UrlGeneric urlGeneric = urlgenericrepository.findByurl(url.trim()); // trim()
        if (urlGeneric != null) {
            JSONObject obj = new JSONObject();
            obj.put("type", "urlGeneric");
                return obj;
        }
        
         Resource duplicateResource = urlResourceRepository.getFirstResourceWithSimilarUrl(url);
         if (duplicateResource == null) {
             if (UrlUtil.getYoutubeVideoId(url) != null) {
                 return importService.getYoutubeInfos(url);							//return json object
             } /* We removed this test. see UrlUtil.isUrlValid for more explanation.
             	 else if (!UrlUtil.isUrlValid(url)) {
            	 JSONObject obj = new JSONObject();
            	 obj.put("type", "invalidUrl");
                 return obj;  } */
               else {
            	 JSONObject obj = new JSONObject();
            	 obj.put("type", "ok");
                 return obj;
             }
         } else {
        	 JSONObject obj = new JSONObject();
             obj.put("type", "duplicateUrl");
             obj.put("value", UrlUtil.getRelativeUrlToResourceDisplay(duplicateResource));
                 return obj;       
         }
    }
    
  
    @RequestMapping("/ajax/resourceaddsubmit1")
//	public @ResponseBody String resourceAddSubmit() {
	public @ResponseBody MessageAndId resourceAddSubmit(@RequestParam(value="url", required=false) String url, 
	        @RequestParam(value="title",required=false) String title,
	        @RequestParam(value="format",required=true) Format format, 
	        @RequestParam(value="platform",required=true) Set<Platform> platforms, 
	        @RequestParam(value="topic",required=true) Topic topic,
	        @RequestParam(value="mincycle",required=true) String minCycle, /* From hidden field */
	        @RequestParam(value="maxcycle",required=true) String maxCycle){  
    
        SecurityContext.assertUserIsLoggedIn();
   
        Resource resource = new Resource();
        
        ////// Find min and max Cycles from slider input
       
        //convert String to int, for Ex: "1.00" to 1 (slider component sends decimal strings...)
        int tempIntMin=(int)Double.parseDouble(minCycle);
        int tempIntMax=(int)Double.parseDouble(maxCycle);
       
        // Get cycle id from slider position (1 -> 300)
        int idMinCycle= ResourceDisplayController.ID_CYCLES_IN_DB[Integer.valueOf(tempIntMin)];
        int idMaxCycle= ResourceDisplayController.ID_CYCLES_IN_DB[Integer.valueOf(tempIntMax)];
             
        Cycle cycleMin = (Cycle) getRequiredEntity(idMinCycle, Cycle.class);
        Cycle cycleMax = (Cycle) getRequiredEntity(idMaxCycle, Cycle.class);
        
        url=url.toLowerCase().trim();
        if(!url.startsWith("http://") && !url.startsWith("https://")){
        	url = "http://" + url;
        }
        resource.setName(title);
        String slug = Slugify.slugify(resource.getName());
        resource.setSlug(slug);
        resource.setFormat(format);
        resource.setPlatforms(platforms);
        resource.setTopic(topic);       
        
        resource.setMinCycle(cycleMin);
        resource.setMaxCycle(cycleMax);
        
        UrlResource urlResource = new UrlResource();
        // urlResource.setName();  The first (and probably only) URL has no name.
        urlResource.setUrl(url);
        urlResource.setResource(resource);
        resource.getUrlResources().add(urlResource);
        
        //resource.setFormat(searchoptions.getFormat().get(0));  
        urlResourceRepository.persist(urlResource);
        resourceRepository.persist(resource);

        
        indexManager.add(resource);
        
        User user=SecurityContext.getUser();
        
        LevelService levelService = new LevelService();
        
        if (levelService.canDoAction(user, Action.ADD_RESOURCE)) {  // If the user adding the resource is trusted for validating other resource, then we can directly trust him for this new resource...
        	resource.setValidationStatus(ValidationStatus.ACCEPT);
        }
       
        contributionService.contribute(user, resource, Action.ADD_RESOURCE, "", "new_value");
        //levelService.addActionPoints(SecurityContext.getUser(), Action.ADD_RESOURCE);
               
        return new MessageAndId(resource.getId(),
                "Ressource ajoutée - <a href="+UrlUtil.getRelativeUrlToResourceDisplay(resource)+">Afficher</a>");
    }
    
    @RequestMapping("/ajax/resourceaddsubmit2")
    public /*@ResponseBody MessageAndId*/ String resourceAddSubmit2(@RequestParam(value="description",required=false) String description,
            @RequestParam(value="idresource",required=false) Long id,
            @RequestParam(value="language",required=false) Language language,
            @RequestParam(value="advertising",required=false) Boolean advertising,
            @RequestParam(value="maxDuration",required=false) Integer duration,
            @RequestParam(value="nature", required=false) Nature nature){
        if (id == null) {
            throw new InvalidUrlException("Missing resource id.");
        }
        Resource resource=getRequiredEntity(id);
        
        
        
		User user = SecurityContext.getUser();
		
		if((description!="") && (description!=null))
			contributionService.contribute(user, resource, Action.EDIT_RESOURCE_DESCRIPTION, "", description);
		if(duration!=null)
			contributionService.contribute(user, resource, Action.EDIT_RESOURCE_DURATION, "", duration.toString());
		if(nature!=null)
			contributionService.contribute(user, resource, Action.EDIT_RESOURCE_NATURE, "", nature.getDescription());

        
        
  
        resource.setLanguage(language);
        resource.setDescription(description);
        resource.setAdvertising(advertising);
        resource.setDuration(duration); 
        resource.setNature(nature);
        resourceRepository.merge(resource); 
        indexManager.update(resource);
        
	
			
        
        
        return "redirect:" + UrlUtil.getRelativeUrlToResourceDisplay(resource);
//        return new MessageAndId(resource.getId(),
//                "Information complémentaires enregistrées - <a href="+UrlUtil.getRelativeUrlToResourceDisplay(resource)+">Afficher la ressource</a>.");
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
