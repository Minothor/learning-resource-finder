package learningresourcefinder.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
import org.springframework.web.bind.annotation.RequestBody;
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
	public ModelAndView resourceImageAdd(@RequestParam("idResource") long resourceid, @RequestParam("file") MultipartFile multipartFile) throws Exception{
		
		Resource resource = resourceRepository.find(resourceid);
		User user = resource.getCreatedBy();
		SecurityContext.assertCurrentUserMayEditThisUser(user);
		
		///// Save original image, scale it and save the resized image.
		try {
			
			FileUtil.uploadFile(multipartFile, FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_ORIGINAL_SUB_FOLDER, 
					FileUtil.assembleImageFileNameWithCorrectExtention(multipartFile, resource.getId() + "-" + (resource.getNumberImage() + 1)));
			
			BufferedImage resizedImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),120 * 200, 200, 200);
						
			ImageUtil.saveImageToFileAsJPEG(resizedImage,  
					FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SUB_FOLDER +  FileUtil.RESOURCE_RESIZED_LARGE_SUB_FOLDER, resource.getId() + "-" + (resource.getNumberImage() + 1) + ".jpg", 0.9f);
			
			BufferedImage resizedSmallImage = ImageUtil.scale(new ByteArrayInputStream(multipartFile.getBytes()),40 * 40, 60, 60);
			
			ImageUtil.saveImageToFileAsJPEG(resizedSmallImage,  
					FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SMALL_SUB_FOLDER, resource.getId() + "-" + (resource.getNumberImage() + 1) + ".jpg", 0.9f);

			resource.setNumberImage(resource.getNumberImage() + 1);
			resourceRepository.merge(resource);
			
		} catch (InvalidImageFileException e) {  
			NotificationUtil.addNotificationMessage(e.getMessageToUser());
		}
		
		ModelAndView mv = new ModelAndView("redirect:/resource/" + resource.getId() + "/" + resource.getName());
		mv.addObject("random", System.currentTimeMillis());
		mv.addObject("canEdit", (SecurityContext.canCurrentUserEditResource(resource)));

		return mv;
	}
	
	@RequestMapping("/imageaddUrl" )
	public ModelAndView resourceImageAddUrl(@RequestParam("idResource") long resourceid, @RequestParam("strUrl") String url) throws Exception {
		
		Resource resource = resourceRepository.find(resourceid);
		User user = resource.getCreatedBy();
		SecurityContext.assertCurrentUserMayEditThisUser(user);
		
		ModelAndView mv = new ModelAndView("redirect:/resource/" + resource.getId() + "/" + resource.getName());
		mv.addObject("random", System.currentTimeMillis());
		mv.addObject("canEdit", (SecurityContext.canCurrentUserEditResource(resource)));

		BufferedImage image = null;
		
		try 
		{
            image = ImageUtil.readImage(url);
        } 
		catch (RuntimeException e) 
		{
        	NotificationUtil.addNotificationMessage("veuillez indiquer une URL valide");
            return mv;//useless to try to save image if we don't have it
        }
		
        try 
        {
            ByteArrayOutputStream outStream= new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outStream);
            
            image = ImageUtil.scale(new ByteArrayInputStream(outStream.toByteArray()),120 * 200, 200, 200);
            
            ImageUtil.saveImageToFileAsJPEG(image, 
            		FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SUB_FOLDER +  FileUtil.RESOURCE_RESIZED_LARGE_SUB_FOLDER, resource.getId() + "-" + (resource.getNumberImage() + 1) + ".jpg", 0.9f);           
            
            BufferedImage resizedImage = ImageUtil.scale(new ByteArrayInputStream(outStream.toByteArray()),40 * 40, 60, 60);

            ImageUtil.saveImageToFileAsJPEG(resizedImage,  
            		FileUtil.getGenFolderPath(currentEnvironment) + 
            		FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SUB_FOLDER + FileUtil.RESOURCE_RESIZED_SMALL_SUB_FOLDER, resource.getId() + "-" + (resource.getNumberImage() + 1) + ".jpg", 0.9f);
            
            resource.setNumberImage(resource.getNumberImage() + 1);
			resourceRepository.merge(resource);
        } 
        catch (IOException e) 
        {
            throw new RuntimeException(e);
        }

        return mv;
	}
	
	@RequestMapping("/change")
	public ModelAndView resourceImageChange(@RequestBody ArrayList<String> order){	

		String idResource = ((String) (order.toArray()[0])).split("-")[0];
		Resource resource = resourceRepository.find(Long.parseLong(idResource));
		resourceService.changeGalleryOrder(order, resource);
		
		//Resource resource = resourceRepository.find(Long.parseLong(order[0].split("-")[0]));
		
		//resourceService.changeGalleryOrder(arrayList, resource);
		
		return new ModelAndView("redirect:/resource/" + resource.getId() + "/" + resource.getName());
	 }
	
	 @RequestMapping("/delete")
	 public ModelAndView resourceImageDelete(@RequestParam("id") long resrouceid, @RequestParam("img") long imgid){	
		 Resource resource = resourceRepository.find(resrouceid);
		 resourceService.resourceImageDelete(resource, imgid);
		 return new ModelAndView("redirect:/resource/" + resource.getId() + "/" + resource.getName());
		 
	 }

	 private boolean canEdit(User user) {
		 return user.equals(SecurityContext.getUser()) || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS);
	 }
}


