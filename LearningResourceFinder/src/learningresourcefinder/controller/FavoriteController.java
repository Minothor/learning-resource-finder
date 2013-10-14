package learningresourcefinder.controller;

import learningresourcefinder.model.User;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.NotificationUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FavoriteController {
	@RequestMapping("/ajax/addfavorite")
	public @ResponseBody String addfavorite(@RequestParam("idResource")long idResource) {
		if(SecurityContext.isUserLoggedIn()) {
			User u = SecurityContext.getUser();
		} 
		else {
			NotificationUtil.addNotificationMessage("Vous devez être connecté pour ajouter une ressource en favori.");
		}
		
		
		
		return null;
	}
}
