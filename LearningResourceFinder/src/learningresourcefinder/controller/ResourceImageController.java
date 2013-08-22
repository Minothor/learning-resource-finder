package learningresourcefinder.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.security.Privilege;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.ResourceService;
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
@RequestMapping("/resource")
public class ResourceImageController extends BaseController<User> {

	@Autowired ResourceService resourceService;
	@Autowired ResourceRepository resourceRepository;
	@Autowired UserService userService;
	@Autowired CurrentEnvironment currentEnvironment;
	
	@RequestMapping("/image")
	public ModelAndView resourceImage(@RequestParam("id") long userid){
		User user = getRequiredEntity(userid);
		ModelAndView mv= new ModelAndView("resourceimage", "user", user);
		
		return mv;
	}

	@RequestMapping("/imageadd")
	public ModelAndView resourceImageAdd(@RequestParam("id") long userid, @RequestParam("idResource") long resourceid, @RequestParam("file") MultipartFile multipartFile) throws Exception{
		
		Resource resource = resourceRepository.find(resourceid);
		User user = getRequiredEntity(userid);
		SecurityContext.assertCurrentUserMayEditThisUser(user);
		
		///// Save original image, scale it and save the resized image.
		try {
			
			FileUtil.uploadFile(multipartFile, FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.USER_SUB_FOLDER + FileUtil.USER_ORIGINAL_SUB_FOLDER, 
					FileUtil.assembleImageFileNameWithCorrectExtention(multipartFile, Long.toString(user.getId())));
			
			BufferedImage resizedImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),120 * 200, 200, 200);
						
			ImageUtil.saveImageToFileAsJPEG(resizedImage,  
					FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.USER_SUB_FOLDER + FileUtil.USER_RESIZED_SUB_FOLDER +  FileUtil.USER_RESIZED_LARGE_SUB_FOLDER, user.getId() + ".jpg", 0.9f);
			
			BufferedImage resizedSmallImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),40 * 40, 60, 60);
			
			ImageUtil.saveImageToFileAsJPEG(resizedSmallImage,  
					FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.USER_SUB_FOLDER + FileUtil.USER_RESIZED_SUB_FOLDER + FileUtil.USER_RESIZED_SMALL_SUB_FOLDER, user.getId() + ".jpg", 0.9f);

			user.setPicture(true);
			resource.setNumberImage(resource.getNumberImage() + 1);
			
			resourceRepository.merge(resource);
			
		} catch (InvalidImageFileException e) {  
			NotificationUtil.addNotificationMessage(e.getMessageToUser());
		}
		
		ModelAndView mv = new ModelAndView("redirect:/resourcedisplay/" + resource.getId() + "/" + resource.getName());
		mv.addObject("random", System.currentTimeMillis());

		return mv;
	}
	
	 @RequestMapping("/deleteresourceimagedeletegallery")
	 public ModelAndView resourceImageDelete(@RequestParam("id") long userid, @RequestParam("idresource") long resrouceid){
		
		 Resource resource = resourceRepository.find(resrouceid);
		 User user = getRequiredEntity(userid);
		 userService.userImageDelete(user);
		 resourceService.resourceImageDelete(resource) ;

		 resourceRepository.merge(resource);

		 return new ModelAndView("redirect:/resourcedisplay/" + resource.getId() + "/" + resource.getName());
		 
	 }

	 private boolean canEdit(User user) {
		 return user.equals(SecurityContext.getUser()) || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS);
	 }
	
}
