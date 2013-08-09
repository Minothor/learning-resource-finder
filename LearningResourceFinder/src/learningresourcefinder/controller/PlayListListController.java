package learningresourcefinder.controller;

import learningresourcefinder.exception.InvalidUrlException;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/playlist")
public class PlayListListController  extends BaseController<PlayList> {

	@Autowired UserRepository userRepository;
	
	@RequestMapping("/user/{userName}")
	public ModelAndView displayList(@PathVariable("userName") String userName) {
		User user = userRepository.getUserByUserName(userName);
        if (user == null) {
        	throw new InvalidUrlException("L'utilisateur ayant le pseudonyme (userName) '"+userName+"' est introuvable.");
        }
        
		return new ModelAndView("playlistlist", "playlistlist", user.getPlaylist());
	}

}
