package learningresourcefinder.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class UrlResourceController extends BaseController<UrlResource> {
	
	
	@PersistenceContext 
	EntityManager em; 
	@RequestMapping("/removeurlresource")
	public ModelAndView removeResource(@RequestParam("id") long id) {
		UrlResource urlResource = getRequiredEntity(id); 
		Resource resource = urlResource.getResource();

		resource.getUrlResource().remove(id);
		em.remove(urlResource); 
		
		
		
		 return new ModelAndView ("redirect:/resource?id="+resource.getId());
	}
	
		
	@RequestMapping("/addurl")
	public ModelAndView urlSubmit (UrlResource urlresource, BindingResult bindingResult) {

		Resource resource= urlresource.getResource(); 

		if (bindingResult.hasErrors() ){

			return new ModelAndView("redirect:/resoure?id"+resource.getId()); 
		}
		em.persist(urlresource); 

		return new ModelAndView("redirect:/resource?id="+resource.getId());
		
	}
	
}
