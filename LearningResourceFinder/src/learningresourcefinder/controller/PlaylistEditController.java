package learningresourcefinder.controller;

import javax.validation.Valid;

import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.PlayListRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.UrlUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/playlist")
public class PlaylistEditController extends BaseController<PlayList>{
	@Autowired PlayListRepository playlistRepository;
	
	 private ModelAndView prepareModelAndView(PlayList playlist ) {
	        ModelAndView mv = new ModelAndView("playlistedit");
	        mv.addObject("id", playlist.getId());
	        mv.addObject("playlist", playlist); 
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
	public ModelAndView playListEditSubmit(@Valid @ModelAttribute PlayList playList, BindingResult bindingResult){
		if (bindingResult.hasErrors()){

			return new ModelAndView("playlistedit","playlist",playList);
		}
		
		PlayList playListHavingTheSameName = playlistRepository.findByNameAndAuthor(playList.getName(), SecurityContext.getUser());

		// set the slug based on the (maybe changed) title
		//playlist.setSlug( tu vas appeler une fonction qui prend playList.getTitle() en paramètre );
		String slug = UrlUtils.toPrettyURL(playList.getName());
		playList.setSlugs(slug);
		if(playList.getId()==null) {  // Create
			if(playListHavingTheSameName != null ) {
				return otherPlayListError(playList, playListHavingTheSameName, bindingResult);
			} 
			playlistRepository.persist(playList);
			
		} else {  // Edit existing playlist
			if (playListHavingTheSameName != null && !playList.equals(playListHavingTheSameName)) {
				return otherPlayListError(playList, playListHavingTheSameName, bindingResult);
			}
			playlistRepository.merge(playList);
		}
		
		return new ModelAndView("redirect:/playlist/"+playList.getId()+"/"+playList.getSlug());
	}
	
	private ModelAndView otherPlayListError(PlayList playList, PlayList otherPlayList, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView ("playlistedit", "playlist", playList);
		bindingResult.rejectValue("name", "sss", "Une de vos playList a déjà ce titre. Merci d'en choisir un différent.");
		return mv;
	}

	
	@RequestMapping("/remove")
    public ModelAndView playListRemoveResource(@RequestParam("idplaylist")long idPlayList,@RequestParam("idresource")long idResource) {
        PlayList playlist = (PlayList) getRequiredEntity(idPlayList,PlayList.class);
        Resource resource = (Resource) getRequiredEntity(idResource,Resource.class);
        
        if (playlist!= null && resource!=null){
            playlist.getResourceList().remove(resource);
            playlistRepository.merge(playlist);
        }
        return new ModelAndView("redirect:/playlist?id="+playlist.getId());
    }
	
	@RequestMapping("/add")
    public ModelAndView playListAddResource(@RequestParam("idplaylist")long idPlayList,@RequestParam("idresource")long idResource) {
        PlayList playlist = (PlayList) getRequiredEntity(idPlayList,PlayList.class);
        Resource resource = (Resource) getRequiredEntity(idResource,Resource.class);
        
        if (playlist!= null && resource!=null){
            playlist.getResourceList().add(resource);
            playlistRepository.merge(playlist);
        }
        return new ModelAndView("redirect:/playlist/"+playlist.getId()+"/"+playlist.getSlug());
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
