package learningresourcefinder.controller;

import learningresourcefinder.model.PlayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlayListDisplayController extends BaseController<PlayList> {

	@RequestMapping("/playlist/{id}/{slug}")
	public ModelAndView prepareModelAndView(@PathVariable long id) {
		PlayList playlist = getRequiredEntity(id);
		return new ModelAndView("playlistdisplay", "playlist", playlist);
	}


}
