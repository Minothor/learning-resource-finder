package learningresourcefinder.controller;

import learningresourcefinder.model.PlayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlayListDisplayController extends BaseController<PlayList> {

	@RequestMapping("/playlist/{shortId}/{slug}")
	public ModelAndView prepareModelAndView(@PathVariable String shortId) {
		PlayList playlist = getRequiredEntityByShortId(shortId);
		return new ModelAndView("playlistdisplay", "playlist", playlist);
	}


}
