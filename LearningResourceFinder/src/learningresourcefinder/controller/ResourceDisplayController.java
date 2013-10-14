package learningresourcefinder.controller;

import learningresourcefinder.exception.InvalidUrlException;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.service.LevelService;
import learningresourcefinder.util.Action;
import learningresourcefinder.util.EnumUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;
import learningresourcefinder.web.Slugify;
import learningresourcefinder.web.UrlUtil;

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
    @Autowired UrlResourceRepository urlResourceRepository;
    @Autowired IndexManagerService indexManager;
    @Autowired LevelService levelService;
    
    @RequestMapping({"/resource/{shortId}/{slug}",
        "/resource/{shortId}/", // SpringMVC needs us to explicitely specify that the {slug} is optional.   
        "/resource/{shortId}" // SpringMVC needs us to explicitely specify that the "/" is optional.    
    })  
    
    public ModelAndView displayResourceByShortId(@PathVariable String shortId) {   
        Resource resource = getRequiredEntityByShortId(shortId);
        ModelAndView mv = new ModelAndView("resourcedisplay", "resource", resource);
        
        boolean canvote = false;
        if(SecurityContext.isUserLoggedIn()){
        	User user = SecurityContext.getUser();
            canvote = levelService.canDoAction(user, Action.VOTE);
        }
		mv.addObject("usercanvote",canvote);
        
    	mv.addObject("canEdit", (SecurityContext.canCurrentUserEditResource(resource)));
    	addDataEnumPlatformToModelAndView(mv, Platform.class);
    	return mv;
	}

	@RequestMapping("/ajax/resourceeditfieldsubmit")
	public @ResponseBody String resourceAddSubmit(@RequestParam("pk") Long id, @RequestParam("value") String value, @RequestParam ("name") String fieldName) {
		
		SearchOptions searchOptions=new SearchOptions();
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
		else if(fieldName.equals("platform")){
			resource.setPlatform(searchOptions.getPlatform().get(Integer.parseInt(value)-1));
		}
		resourceRepository.merge(resource);

		indexManager.update(resource);

		return "success";
	}



	public void addDataEnumPlatformToModelAndView(ModelAndView mv, Class enumClass) {
		int i = 1, sizeplatform = EnumUtil.getValues(enumClass).length;
		String dataEnumPlatform = "[";

		for (Object enumValue : EnumUtil.getValues(enumClass)) {
			if (i == sizeplatform) {
				dataEnumPlatform = dataEnumPlatform + "{value:" + i + "," + "text:" + "'" + EnumUtil.getDescription(enumValue) + "'}]";
				break;
			}
			dataEnumPlatform = dataEnumPlatform + "{value:" + i + "," + "text:" + "'" + EnumUtil.getDescription(enumValue) + "'},";
			i++;
		}

		mv.addObject("dataEnumPlatform",dataEnumPlatform );
	}

    
    @RequestMapping("/removeurlresource")
    public ModelAndView removeResource(@RequestParam("id") long id) {
        UrlResource urlResource = (UrlResource)getRequiredEntity(id, UrlResource.class); 
        Resource resource = urlResource.getResource();

        resource.getUrlResources().remove(id);
        em.remove(urlResource); 
        NotificationUtil.addNotificationMessage("Url supprimée avec succès.<br/>"+urlResource.getUrl(),Status.SUCCESS);
        return new ModelAndView ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }
    
    
    @RequestMapping("/urlresourceeditsubmit")
    public String urlSubmit(@RequestParam(value="urlresourceid", required=false) Long urlResourceId, @RequestParam("resourceid") long resourceId, @RequestParam("name") String name, @RequestParam("url") String url) 
    {
        // TODO: voir si url est valide (sinon ajouter une erreur sur result)
        
        Resource resource = resourceRepository.find(resourceId);
        if(true) {   // No errors
            UrlResource urlResource;
            if (urlResourceId != null) {
                urlResource = urlResourceRepository.find(urlResourceId);
            } else {
                urlResource = new UrlResource();
                if (resource == null) {
                    throw new InvalidUrlException("Invalid resource id " + resourceId);
                }
                urlResource.setResource(resource);
            }
            
            urlResource.setName(name);
            urlResource.setUrl(url);
            
            if (urlResource.getId() == null) {  // Create
                urlResourceRepository.persist(urlResource);
                NotificationUtil.addNotificationMessage("Url ajoutée avec succès", Status.SUCCESS);         
            } else {  // Edit
                urlResourceRepository.merge(urlResource);
                NotificationUtil.addNotificationMessage("Url modifiée avec succès", Status.SUCCESS);         
            }
            
            
        } else {
            NotificationUtil.addNotificationMessage("Erreur sur un des champs. Url non sauvée.", Status.ERROR);
        }
        
        return ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }

}
