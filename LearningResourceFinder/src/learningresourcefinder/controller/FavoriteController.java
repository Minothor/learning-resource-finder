package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.Favorite;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.FavoriteRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.NotificationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FavoriteController extends BaseController<Favorite> {
	
	@Autowired ResourceRepository resourceRepository;
	@Autowired FavoriteRepository favoriteRepository;
	
	// ADD OR DEL Favorite for a resource
	@RequestMapping("/ajax/addfavorite")
	public ModelAndView addfavorite(@RequestParam("idResource")long idResource) {
		ModelAndView mv = new ModelAndView("favorite");
		if(SecurityContext.isUserLoggedIn()) {
			User user = SecurityContext.getUser();	
			Resource resource = (Resource)getRequiredEntity(idResource, Resource.class);
			
			if(favoriteRepository.isFavorite(resource, user)) {  // User unlike this resource
				Favorite favorite = favoriteRepository.findFavorite(resource, user);
				favoriteRepository.remove(favorite);
				
			} else { // new like on this resource
				Favorite favorite = new Favorite();
				favorite.setUser(user);
				favorite.setResource(resource);
				user.setFavorite(resource);
				favoriteRepository.persist(favorite);
			}
			mv.addObject("isFavorite", favoriteRepository.isFavorite(resource, user));
			mv.addObject("resource", resource);
		} 
		return mv;
	}
	
	@RequestMapping("/favoris/{user}")
	public ModelAndView favoris(@PathVariable("user") String userName) {
		ModelAndView mv = new ModelAndView("resourcelist");
		User user = SecurityContext.getUser();
		List<Resource> listResource = favoriteRepository.findAllResourceFavoriteForUser(user);
		mv.addObject("resourceList", listResource);
	    //mv.addObject("topic", topic);
		mv.addObject("problemTitle","Liste des favoris");
		
		return mv;
	}
}
