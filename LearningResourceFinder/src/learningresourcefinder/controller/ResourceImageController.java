package learningresourcefinder.controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.ResourceService;
import learningresourcefinder.service.UserService;
import learningresourcefinder.util.CurrentEnvironment;
import learningresourcefinder.util.ImageUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.web.UrlUtil;

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
	
	@RequestMapping("/image")  // FIXME Is this method still used? John 2013-12
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
		
		BufferedImage image = null;
		image = ImageUtil.readImage(multipartFile.getBytes());
		
		ImageUtil.createOriginalAndScalesImageFileForResource(resource, image, currentEnvironment);
		
		ModelAndView mv = new ModelAndView("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));

		return mv;
	}


	@RequestMapping("/imageaddUrl" )
	public ModelAndView resourceImageAddUrl(@RequestParam("idResource") long resourceid, @RequestParam("strUrl") String url) throws Exception {
		
		Resource resource = resourceRepository.find(resourceid);
		User user = resource.getCreatedBy();
		SecurityContext.assertCurrentUserMayEditThisUser(user);
		
		ModelAndView mv = new ModelAndView("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));

		BufferedImage image = null;
		
		try {
            image = ImageUtil.readImage(url);
        } catch (RuntimeException e) {
        	NotificationUtil.addNotificationMessage("veuillez indiquer une URL valide");
            return mv;//useless to try to save image if we don't have it
        }
		
		ImageUtil.createOriginalAndScalesImageFileForResource(resource, image, currentEnvironment);

        return mv;
	}

    
    
	@RequestMapping("/change")
	public ModelAndView resourceImageChange(@RequestBody ArrayList<String> order){	

		String idResource = ((String) (order.toArray()[0])).split("-")[0];
		Resource resource = resourceRepository.find(Long.parseLong(idResource));
		resourceService.changeGalleryOrder(order, resource);
		
		//Resource resource = resourceRepository.find(Long.parseLong(order[0].split("-")[0]));
		
		//resourceService.changeGalleryOrder(arrayList, resource);
		
		return new ModelAndView("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
	 }
	
	 @RequestMapping("/delete")
	 public ModelAndView resourceImageDelete(@RequestParam("id") long resrouceid, @RequestParam("img") long imgid){	
		 Resource resource = resourceRepository.find(resrouceid);
		 resourceService.resourceImageDelete(resource, imgid);
		 return new ModelAndView("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
		 
	 }

}


