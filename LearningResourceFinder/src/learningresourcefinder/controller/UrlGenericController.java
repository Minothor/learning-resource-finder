package learningresourcefinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import learningresourcefinder.model.UrlGeneric;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.Role;
import learningresourcefinder.repository.BaseRepository;
import learningresourcefinder.repository.UrlGenericRepository;
import learningresourcefinder.security.SecurityContext;


@Controller
public class UrlGenericController  extends BaseRepository<User> {

	@Autowired UrlGenericRepository urlgenericrepository;


	@RequestMapping(value="/checkUrlGeneric")
	public @ResponseBody String checkUrlGeneric(@RequestParam("url") String url) {
		SecurityContext.assertUserHasRole(Role.ADMIN);
		
		if(url.endsWith("/")){
    		url = url.substring(0, url.length()-1);
    	}
		
		UrlGeneric urlGenericToAdd = urlgenericrepository.findByUrl(url.trim());	
		if(urlGenericToAdd==null){
			urlGenericToAdd = new UrlGeneric();
			urlGenericToAdd.setUrl(url);
			urlgenericrepository.persist(urlGenericToAdd);	
			return "ok";
		} else {
			return "ko";
		}    
	}


	@RequestMapping("/urlGeneric")
	public ModelAndView adminUrlMng() {
		SecurityContext.assertUserHasRole(Role.ADMIN);
		ModelAndView mv = new ModelAndView("urlGenericList");
		List<UrlGeneric> urlList = urlgenericrepository.findAllUrlGeneric();
		mv.addObject("urlList", urlList);

		return mv;
	}  


	@RequestMapping(value="/deleteUrlGeneric")
	public ModelAndView deleteUrlGeneric(@RequestParam("id") Long id) {
		SecurityContext.assertUserHasRole(Role.ADMIN);
		
		UrlGeneric urlGenericToRemove = urlgenericrepository.find(id);
		if(urlGenericToRemove != null){
		    urlgenericrepository.remove(urlGenericToRemove);
		}
        return new ModelAndView("redirect:/urlGeneric");
	}


}







