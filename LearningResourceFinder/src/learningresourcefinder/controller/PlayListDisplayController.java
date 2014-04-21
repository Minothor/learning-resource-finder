package learningresourcefinder.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.PlayListRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.util.CurrentEnvironment;
import learningresourcefinder.util.FileUtil;
import learningresourcefinder.util.ImageUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.FileUtil.InvalidImageFileException;
import learningresourcefinder.web.ModelAndViewUtil;
import learningresourcefinder.web.Slugify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
		PlayList playlist = getRequiredEntityByShortId(shortId);
		
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
		
		ModelAndView mv = new ModelAndView("redirect:/playlist/" + playlistid +"/"+playlist.getName());
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
    public @ResponseBody String playListEditSubmit(@RequestParam("pk") Long id, @RequestParam("value") String value, @RequestParam ("name") String fieldName) {
        
        
        PlayList playlist = getRequiredEntity(id);
        SecurityContext.assertCurrentUserMayEditThisPlaylist(playlist);

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

        return "success";
    }
}
