package learningresourcefinder.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.PlayListRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.util.CurrentEnvironment;
import learningresourcefinder.util.FileUtil;
import learningresourcefinder.util.FileUtil.InvalidImageFileException;
import learningresourcefinder.util.ImageUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.web.ModelAndViewUtil;
import learningresourcefinder.web.Slugify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlayListDisplayController extends BaseController<PlayList> {
	@Autowired CurrentEnvironment currentEnvironment;
	@Autowired PlayListRepository playlistRepository;
	@Autowired IndexManagerService indexManager;
	
    @RequestMapping({"/playlist/{shortId}/{slug}",
        "/playlist/{shortId}/", // SpringMVC needs us to explicitely specify that the {slug} is optional.   
        "/playlist/{shortId}" // SpringMVC needs us to explicitely specify that the "/" is optional.    
    }) 
	public ModelAndView playListDisplay(@PathVariable String shortId) {
        PlayList playlist = playlistRepository.getPlayListByShortId(shortId);
        
        if (playlist ==null) {
            return new ModelAndView("playlistnotfound");
        }
		
		ModelAndView mv = new ModelAndView("playlistdisplay", "playlist", playlist);
    	mv.addObject("canEdit", (SecurityContext.canCurrentUserEditPlayList(playlist)));
    	ModelAndViewUtil.addRatingMapToModelAndView(mv, playlist.getResources());
		return mv;
	}
    
    
    @RequestMapping("/playlist/imageadd")
	public ModelAndView userImageAdd( @RequestParam("idPlayList") long playlistid, @RequestParam("file") MultipartFile multipartFile) throws Exception{
		
		User user = SecurityContext.getUser();
		PlayList playlist = getRequiredEntity(playlistid);

		SecurityContext.assertCurrentUserMayEditThisUser(user);
		
		///// Save original image, scale it and save the resized image.
		try {
			FileUtil.uploadFile(multipartFile, FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.PLAYLIST_SUB_FOLDER + FileUtil.PLAYLIST_ORIGINAL_SUB_FOLDER, 
					FileUtil.assembleImageFileNameWithCorrectExtention(multipartFile, Long.toString(playlist.getId())));

			BufferedImage resizedImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),120 * 200, 200, 200);
						
			ImageUtil.saveImageToFileAsJPEG(resizedImage,  
					FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.PLAYLIST_SUB_FOLDER + FileUtil.PLAYLIST_RESIZED_SUB_FOLDER +  FileUtil.PLAYLIST_RESIZED_LARGE_SUB_FOLDER, playlist.getId() + ".jpg", 0.9f);
			
			BufferedImage resizedSmallImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),40 * 40, 60, 60);
			
			ImageUtil.saveImageToFileAsJPEG(resizedSmallImage,  
					FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.PLAYLIST_SUB_FOLDER + FileUtil.PLAYLIST_RESIZED_SUB_FOLDER + FileUtil.PLAYLIST_RESIZED_SMALL_SUB_FOLDER, playlist.getId() + ".jpg", 0.9f);

			playlist.setPicture(true);
			
			playlistRepository.merge(playlist);
			
		} catch (InvalidImageFileException e) {  //Tell the user that its image is invalid.
			NotificationUtil.addNotificationMessage(e.getMessageToUser());
		}


		ModelAndView mv = new ModelAndView("redirect:/playlist/" + playlistid +"/"+playlist.getName());
		mv.addObject("random", System.currentTimeMillis());

		return mv;
	}
    
    @RequestMapping("/playlist/imageaddUrl" )
	public ModelAndView userImageAdd(@RequestParam("idPlayList") long playlistid, @RequestParam("strUrl") String url) throws Exception {
		
		User user = SecurityContext.getUser();
		PlayList playlist = getRequiredEntity(playlistid);
		
		SecurityContext.assertCurrentUserMayEditThisUser(user);
		
		ModelAndView mv = new ModelAndView("redirect:/playlist/" + playlist.getShortId() +"/"+playlist.getName());
		mv.addObject("random", System.currentTimeMillis());

		BufferedImage image = null;
		
		try {
            image = ImageUtil.readImage(url);
        } catch (RuntimeException e) {
        	NotificationUtil.addNotificationMessage("veuillez indiquer une URL valide");
            return mv; //useless to try to save image if we don't have it
        }
		
        try {
            ByteArrayOutputStream outStream= new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outStream);
            
            image = ImageUtil.scale(new ByteArrayInputStream(outStream.toByteArray()),120 * 200, 200, 200);
            
            ImageUtil.saveImageToFileAsJPEG(image, 
            		FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.PLAYLIST_SUB_FOLDER + FileUtil.PLAYLIST_RESIZED_SUB_FOLDER +  FileUtil.PLAYLIST_RESIZED_LARGE_SUB_FOLDER, playlist.getId() + ".jpg", 0.9f);           
            
            BufferedImage resizedImage = ImageUtil.scale(new ByteArrayInputStream(outStream.toByteArray()),40 * 40, 60, 60);

            ImageUtil.saveImageToFileAsJPEG(resizedImage,  
            		FileUtil.getGenFolderPath(currentEnvironment) + 
            		FileUtil.PLAYLIST_SUB_FOLDER + FileUtil.PLAYLIST_RESIZED_SUB_FOLDER + FileUtil.PLAYLIST_RESIZED_SMALL_SUB_FOLDER, playlist.getId() + ".jpg", 0.9f);

            playlist.setPicture(true);
            
            playlistRepository.merge(playlist);
        } catch (IOException e)  {
            throw new RuntimeException(e);
        }

        return mv;
	}
    
    @RequestMapping("/ajax/playlisteditfieldsubmit")
    public @ResponseBody ResponseEntity<String> playListEditSubmit(@RequestParam("pk") Long id, @RequestParam("value") String value, @RequestParam ("name") String fieldName) {
        
        PlayList playlist = getRequiredEntity(id);
        SecurityContext.assertCurrentUserMayEditThisPlaylist(playlist);        
            
        if (value == null) {
            return new ResponseEntity<String>("Vous n'avez pas introduit de nom.", HttpStatus.BAD_REQUEST);
        }
        
        if (value.length()>= 50) {
            return new ResponseEntity<String>("Le titre de la séquence ne peut pas dépasser 50 caractères.", HttpStatus.BAD_REQUEST);
        }
        
        PlayList playListHavingTheSameName = playlistRepository.findByNameAndAuthor(value, SecurityContext.getUser());

        if (playListHavingTheSameName != null) {
            return new ResponseEntity<String>("Cet intitulé existe déjà pour une de vos séquences, veuillez en choisir un autre",HttpStatus.BAD_REQUEST);
        }
          
        if (fieldName.equals("name")){
            playlist.setName(value);
            String slug = Slugify.slugify(playlist.getName());
            playlist.setSlug(slug);
        }
        else if(fieldName.equals("description")){
            playlist.setDescription(value);
        }
        
        
        playlistRepository.merge(playlist);

        indexManager.update(playlist);

        return new ResponseEntity<String>("sucess",HttpStatus.OK);
    }

    @RequestMapping("/sortresources")
    public @ResponseBody ResponseEntity<String> sortResources(@RequestBody ArrayList<String> order /*example: "15-1452", 15-2555"*/){
        
        // First position(s) before character "-" contains playlist id
        String idPlaylist = ((String) (order.toArray()[0])).split("-")[0];
        PlayList playlist = getRequiredEntity(Long.parseLong(idPlaylist));        

        SecurityContext.assertCurrentUserMayEditThisPlaylist(playlist);

        // Keep a copy of current resources and clear all resources for current playlist
        List<Resource> resourcesListCopy = new ArrayList(playlist.getResources());
        playlist.getResources().clear();  
        
        // We refill playlist.getResources() (now empty) with resources in the same order as order array
        for (String orderItem : order) {
            if (orderItem.trim().length()>0) { // jQuery drag-drop ajax sends us blanks lines, we don't know why...
                // Position(s) after character '-' contains resource id
                Long resId = Long.parseLong(orderItem.substring(orderItem.indexOf("-")+1, orderItem.length()).trim());
                
                // Search resource with Id retrieved and add it into list of resources for current playlist
                for (Resource resourceCopy : resourcesListCopy) {
                    if (resourceCopy.getId().equals(resId)) {
                        playlist.getResources().add(resourceCopy);
                        break;
                    }
                }
            }
        }

        playlistRepository.merge(playlist);
        
        return new ResponseEntity<String>(HttpStatus.OK);  // The Javascript caller will reload the page.
     }

}
