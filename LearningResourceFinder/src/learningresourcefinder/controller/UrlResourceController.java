package learningresourcefinder.controller;

import javax.validation.Valid;


import learningresourcefinder.exception.InvalidUrlException;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;
import learningresourcefinder.web.UrlUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class UrlResourceController extends BaseController<UrlResource> {
	
	@Autowired ResourceRepository resourceRepository;
	@Autowired UrlResourceRepository urlResourceRepository;
	
	@RequestMapping("/removeurlresource")
	public ModelAndView removeResource(@RequestParam("id") long id) {
		UrlResource urlResource = getRequiredEntity(id); 
		Resource resource = urlResource.getResource();

		resource.getUrlResources().remove(id);
		em.remove(urlResource); 
	
		return new ModelAndView ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
	}
	
	
	@RequestMapping("/urleditsubmit")
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
