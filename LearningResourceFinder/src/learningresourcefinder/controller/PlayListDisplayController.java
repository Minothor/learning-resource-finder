package learningresourcefinder.controller;

import learningresourcefinder.model.PlayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlayListDisplayController extends BaseController<PlayList> {

	@RequestMapping("/playlist")
	public ModelAndView prepareModelAndView(@RequestParam("id") long id) {
		PlayList playlist = getRequiredEntity(id);
		return new ModelAndView("playlistdisplay", "playlist", playlist);
	}


}
