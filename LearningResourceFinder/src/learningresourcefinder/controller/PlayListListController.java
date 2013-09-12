package learningresourcefinder.controller;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import learningresourcefinder.exception.InvalidUrlException;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/playlist")
public class PlayListListController  extends BaseController<PlayList> {

	@Autowired UserRepository userRepository;
	
	@RequestMapping("/user/{userName}")
	public String displayList(@PathVariable("userName") String userName, Model model) {
		User user = userRepository.getUserByUserName(userName);
        if (user == null) {
        	throw new InvalidUrlException("L'utilisateur ayant le pseudonyme (userName) '"+userName+"' est introuvable.");
        }
        
        
        // We want the playlist list sorted by name
        Comparator<PlayList> comparator = new Comparator<PlayList>() {
            @Override  public int compare(PlayList pl1, PlayList pl2) {
                return pl1.getName().compareToIgnoreCase(pl2.getName());
            }
        };
        SortedSet<PlayList> playListSet =  new TreeSet<PlayList>(comparator);
        playListSet.addAll(user.getPlayListList());

		//return "playlistlist", 
		model.addAttribute("playlistlist", playListSet);
		model.addAttribute("user", user);
		return "playlistlist";
	}

}
