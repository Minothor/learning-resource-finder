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
public class FavoriteController {
	
	@Autowired ResourceRepository resourceRepository;
	@Autowired FavoriteRepository favoriteRepository;
	
	@RequestMapping("/ajax/addfavorite")
	public @ResponseBody String addfavorite(@RequestParam("idResource")long idResource) {
		
		if(SecurityContext.isUserLoggedIn()) {
			User user = SecurityContext.getUser();	
			Resource resource = resourceRepository.find(idResource);
			Favorite favorite = new Favorite();
			favorite.setUser(user);
			favorite.setResource(resource);
			favoriteRepository.persist(favorite);
			user.setFavorite(resource);
		} 
		else {
			NotificationUtil.addNotificationMessage("Vous devez être connecté pour ajouter une ressource en favori.");
		}

		return "ok";
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
