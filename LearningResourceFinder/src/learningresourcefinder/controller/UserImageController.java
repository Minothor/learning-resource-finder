package learningresourcefinder.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import learningresourcefinder.model.User;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.Privilege;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.UserService;
import learningresourcefinder.util.CurrentEnvironment;
import learningresourcefinder.util.FileUtil;
import learningresourcefinder.util.FileUtil.InvalidImageFileException;
import learningresourcefinder.util.ImageUtil;
import learningresourcefinder.util.NotificationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserImageController extends BaseController<User> {

	@Autowired UserRepository userRepository;
	@Autowired UserService userService;
	@Autowired CurrentEnvironment currentEnvironment;
	


	@RequestMapping("/imageadd")
	public ModelAndView userImageAdd(@RequestParam("id") long userid, @RequestParam("file") MultipartFile multipartFile) throws Exception{
		
		User user = getRequiredEntity(userid);
		SecurityContext.assertCurrentUserMayEditThisUser(user);
		
		///// Save original image, scale it and save the resized image.
		try {
		    String originalImageFolderPath = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.USER_SUB_FOLDER + FileUtil.USER_ORIGINAL_SUB_FOLDER;
		    String originalImageFileName = FileUtil.assembleImageFileNameWithCorrectExtention(multipartFile, Long.toString(user.getId()));
			FileUtil.uploadFile(multipartFile, originalImageFolderPath, originalImageFileName);
		
			// TODO: Tomas, refactor here to ;-)
			BufferedImage originalImage = ImageIO.read(new File(originalImageFolderPath + "/" + originalImageFileName));
			        	
	        userService.addOrUpdateUserImage(user, originalImage, false);
			
		} catch (InvalidImageFileException e) {  //Tell the user that his image is invalid.
			NotificationUtil.addNotificationMessage(e.getMessageToUser());
		}
		
		
		ModelAndView mv = new ModelAndView("redirect:/user/" + user.getUserName());
		mv.addObject("random", System.currentTimeMillis());
		
		return mv;
	}
	
	@RequestMapping("/imageaddUrl" )
	public ModelAndView userImageAdd(@RequestParam("id") long userid, @RequestParam("strUrl") String url) throws Exception {
		
		User user = getRequiredEntity(userid);
		SecurityContext.assertCurrentUserMayEditThisUser(user);
		
		ModelAndView mv = new ModelAndView("redirect:/user/" + user.getUserName());
		mv.addObject("random", System.currentTimeMillis());

		BufferedImage image = null;
		
		try {
            image = ImageUtil.readImage(url);
        } catch (RuntimeException e) {
        	NotificationUtil.addNotificationMessage("veuillez indiquer une URL valide");
            return mv;//useless to try to save image if we don't have it
        }
		
        userService.addOrUpdateUserImage(user, image, true);

        return mv;
	}

	
	 @RequestMapping("/imagedelete")
	 public ModelAndView userImageDelete(@RequestParam("id") long userid){
		 User user = getRequiredEntity(userid);
		 userService.userImageDelete(user);
		 userRepository.merge(user);

		 return new ModelAndView("redirect:/user/" + user.getUserName());
		 
	 }

	 private boolean canEdit(User user) {
		 return user.equals(SecurityContext.getUser()) || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS);
	 }
	 


	 
	
}
