package learningresourcefinder.controller;

import javax.validation.Valid;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class UrlResourceController extends BaseController<UrlResource> {
	
	@Autowired ResourceRepository resourceRepository;
	
	@RequestMapping("/removeurlresource")
	public ModelAndView removeResource(@RequestParam("id") long id) {
		UrlResource urlResource = getRequiredEntity(id); 
		Resource resource = urlResource.getResource();

		resource.getUrlResources().remove(id);
		em.remove(urlResource); 
		
		return new ModelAndView ("redirect:/resource/"+resource.getId()+"/"+resource.getName());
	}
	
	
	@RequestMapping(value="/ajax/addurl",method=RequestMethod.POST)
	public @ResponseBody String urlSubmit(@Valid @ModelAttribute UrlResource urlResource, @RequestParam("idresource") long id, @RequestParam("name") String name, @RequestParam("url") String url, BindingResult result) 
	{
		String returnText;
		
		if(!result.hasErrors()) {
			Resource resource = resourceRepository.find(id);
			UrlResource urlresource = new UrlResource();
			urlresource.setName(name);
			urlresource.setUrl(url);
			urlresource.setResource(resource);
			em.persist(urlresource);
		    returnText = "La ressource à bien été enregistée !";
		}
		else {
		    returnText = "Désolé mais une erreur est survenue...";
		}
		    return returnText;
	}
}
