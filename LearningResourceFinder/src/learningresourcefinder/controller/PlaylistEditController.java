package learningresourcefinder.controller;

import javax.validation.Valid;

import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.PlayListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/playlist")
public class PlaylistEditController extends BaseController<PlayList>{
	@Autowired PlayListRepository playlistRepository;
	
	 private ModelAndView prepareModelAndView(PlayList playlist ) {
	        ModelAndView mv = new ModelAndView("playlistedit");
	        mv.addObject("id", playlist.getId());
	        mv.addObject("playlist",playlist ); 
	        return mv; 
	    }
	
	@RequestMapping("/create")
	public ModelAndView playListCreate() {
		 return prepareModelAndView(new PlayList());
	}
	
	@RequestMapping("/edit")
	public ModelAndView playListEdit (@RequestParam("id")long id) {
		PlayList playlist =(PlayList) getRequiredEntity(id,PlayList.class);
		return prepareModelAndView(playlist);
		
	}
	@RequestMapping("/editsubmit")
	public ModelAndView playListEditSubmit(@Valid @ModelAttribute PlayList playlist, BindingResult bindingResult){
		if (bindingResult.hasErrors()){

			return new ModelAndView("playlistedit","playlist",playlist);
		}
		if(playlist.getId()==null){
			playlistRepository.persist(playlist);
		}
		else {playlistRepository.merge(playlist);


		}
		return new ModelAndView("redirect:/playlist?id="+playlist.getId());
		
	}
	
	@RequestMapping("/remove")
    public ModelAndView playListRemoveResource(@RequestParam("idplaylist")long idPlayList,@RequestParam("idresource")long idResource) {
        PlayList playlist = (PlayList) getRequiredEntity(idPlayList,PlayList.class);
        Resource resource = (Resource) getRequiredEntity(idResource,Resource.class);
        
        if (playlist!= null && resource!=null){
            playlist.getResourceList().remove(resource);
            playlistRepository.persist(playlist);
        }
        return new ModelAndView("redirect:/playlist?id="+playlist.getId());
    }
	
	@RequestMapping("/add")
    public ModelAndView playListAddResource(@RequestParam("idplaylist")long idPlayList,@RequestParam("idresource")long idResource) {
        PlayList playlist = (PlayList) getRequiredEntity(idPlayList,PlayList.class);
        Resource resource = (Resource) getRequiredEntity(idResource,Resource.class);
        
        if (playlist!= null && resource!=null){
            playlist.getResourceList().add(resource);
            playlistRepository.persist(playlist);
        }
        return new ModelAndView("redirect:/playlist?id="+playlist.getId());
    }
	
    @ModelAttribute
    public PlayList findPlayList(@RequestParam(value="id",required=false)Long id){
        if(id==null){
            //create
            return new PlayList(); 
        }else {

            return (PlayList)getRequiredDetachedEntity(id);

        }
    }

}
