package learningresourcefinder.controller;

import javax.validation.Valid;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.web.Slugify;

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
public class ResourceEditController extends BaseController<Resource> {

	@Autowired ResourceRepository resourceRepository; 
	@Autowired UrlResourceRepository urlResourceRepository;
	@RequestMapping("/resourcecreate")
	public ModelAndView resourceCreate() {
		return prepareModelAndView(new Resource() );
	}

//	@RequestMapping("/resourceedit")
//	public ModelAndView resourceEdit(@RequestParam("id") long id){
//        Resource resource=(Resource)getRequiredEntity(id, Resource.class);
//		return prepareModelAndView(resource);
//
//	}
	
    @RequestMapping("/resourceedit")
    public ModelAndView resourceEdit(@RequestParam("id") long id){
        Resource resource=(Resource)getRequiredEntity(id, Resource.class);
        SecurityContext.assertCurrentUserMayEditThisPlaylist(resource);
        return prepareModelAndView(resource);

    }


	private ModelAndView prepareModelAndView(Resource resource) {
		ModelAndView mv=new ModelAndView("resourceedit"); //JSP
		mv.addObject("id", resource.getId()); // will be null in case of create
		mv.addObject("resource", resource);
		return mv;
	}

	@RequestMapping("/resourceeditsubmit")
	public ModelAndView resourceEditSubmit(@Valid @ModelAttribute Resource resource, BindingResult bindingResult) {

		if (bindingResult.hasErrors()){
			return new ModelAndView("resourceedit","resource",resource);
		}
		
		String slug = Slugify.slugify(resource.getName());
		resource.setSlug(slug);
		
		if(resource.getId()==null){
			resourceRepository.persist(resource);
		} else {
			resourceRepository.merge(resource);
		}
		
		return new ModelAndView("redirect:/resource/"+resource.getShortId()+"/"+resource.getSlug());
	}

	@ModelAttribute
	public Resource findResource(@RequestParam(value="id",required=false)Long id){
		if(id==null){
			//create
			return new Resource(); 
		}else {

			return (Resource)getRequiredDetachedEntity(id);

		}
	}
	public int addImageOnDB(){
		return 0;
	}
	

	@RequestMapping(value="/ajax/checkUrl",method=RequestMethod.POST)
	public @ResponseBody boolean urlSubmit(@RequestParam("url") String url) {
		 Boolean checkUrlOk = false;
		 if(urlResourceRepository.getResourceByUrl(url) != null)
			checkUrlOk =true;
		else
	    	checkUrlOk =false;
		
		return checkUrlOk;
	}

}



