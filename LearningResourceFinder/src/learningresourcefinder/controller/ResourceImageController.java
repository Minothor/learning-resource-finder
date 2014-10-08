package learningresourcefinder.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.ResourceService;
import learningresourcefinder.service.UserService;
import learningresourcefinder.util.CurrentEnvironment;
import learningresourcefinder.util.FileUtil;
import learningresourcefinder.util.ImageUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.web.UrlUtil;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/resource")
public class ResourceImageController extends BaseController<User> {

	@Autowired ResourceService resourceService;
	@Autowired ResourceRepository resourceRepository;
	@Autowired UserService userService;
	@Autowired CurrentEnvironment currentEnvironment;
	


	@RequestMapping("/imageadd")
	public ModelAndView resourceImageAdd(@RequestParam("idResource") long resourceid, @RequestParam("file") MultipartFile multipartFile) throws Exception{
		
		Resource resource = resourceRepository.find(resourceid);
		SecurityContext.assertCurrentUserMayEditThisResource(resource);
		
		BufferedImage image = null;
		image = ImageUtil.readImage(multipartFile.getBytes());
		
		ImageUtil.createOriginalAndScalesImageFileForResource(resource, image, currentEnvironment);
		
		ModelAndView mv = new ModelAndView("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));

		return mv;
	}

	@RequestMapping("/ajax/resourceImageClipBoardBeforeCrop")
	public @ResponseBody String resourceImageClipBoardBeforeCrop(HttpServletRequest request, @RequestParam("resourceid") long resourceid) throws ServletException, IOException {
	    Resource resource = resourceRepository.find(resourceid);
	    SecurityContext.assertCurrentUserMayEditThisResource(resource);

        // Save this image with prefix "tmp-". It's a temporary file until the user crops the image and calls resourceImageClipBoardAfterCrop
        String path = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_ORIGINAL_SUB_FOLDER;
        String fileName = "tmp-" + resource.getId() + "-" + (resource.getNumberImage() + 1) + ".png";

        // We save the image as PNG (for crop) instead of JPEG because on Mac/Safari, JPEG do not work in the crop page (don't know why) -- Bruno 2014-09-16
	    ImageUtil.saveImageToFileAsPNG(request.getInputStream(), path, fileName);

	    return fileName;
	}


    @RequestMapping("/ajax/resourceImageClipBoardAfterCrop")
    public ModelAndView resourceImageClipBoardAfterCrop( 
            @RequestParam(value = "xCoord") String x,
            @RequestParam(value = "yCoord") String y,
            @RequestParam(value = "wCoord") String w,
            @RequestParam(value = "hCoord") String h,
            @RequestParam(value = "imageFileName") String imageFileName,
            @RequestParam(value = "resourceid") long id
            ) throws IOException {
  
        Resource resource = resourceRepository.find(id);
        SecurityContext.assertCurrentUserMayEditThisResource(resource);

        String path = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_ORIGINAL_SUB_FOLDER;
        
        File tmpFile = new File(path, imageFileName);
        
        BufferedImage outImage = ImageIO.read(tmpFile);
        BufferedImage cropImage;
        
        outImage = ImageUtil.readImage(outImage);
        
        if (x != null && y != null && w != null && h != null) {
            
            int cropx = (int) Double.parseDouble(x);
            int cropy = (int) Double.parseDouble(y);
            int cropw = (int) Double.parseDouble(w);
            int croph = (int) Double.parseDouble(h);            
            //I check if the crop is not 0 to avoid the exception
            //I check after the conversion because the crop != "0"  does not work
            if (cropx != 0 && cropy != 0 && cropw != 0 && croph != 0){
               
                cropImage = outImage.getSubimage(cropx, cropy, cropw, croph);
            } else {
                
                cropImage = outImage;
             
            }

        } else {
            cropImage = outImage;            
        }
     
        ImageUtil.createOriginalAndScalesImageFileForResource(resource, cropImage, currentEnvironment);
        
        // Delete temporary file
        if (tmpFile.exists()) {
            tmpFile.delete();
        }

        return new ModelAndView("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }
    
    
	@RequestMapping("/imageaddUrl" )
	public ModelAndView resourceImageAddUrl(@RequestParam("idResource") long resourceid, @RequestParam("strUrl") String url) throws Exception {
		
		Resource resource = resourceRepository.find(resourceid);
		SecurityContext.assertCurrentUserMayEditThisResource(resource);
		
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
    
	@RequestMapping("/imagechange") 
	public ModelAndView resourceImageChange(@RequestBody ArrayList<String> order){	

		String idResource = ((String) (order.toArray()[0])).split("-")[0];
		Resource resource = resourceRepository.find(Long.parseLong(idResource));
		resourceService.changeGalleryOrder(order, resource);
		
		//Resource resource = resourceRepository.find(Long.parseLong(order[0].split("-")[0]));
		
		//resourceService.changeGalleryOrder(arrayList, resource);
		
		return new ModelAndView("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
	 }
	
	 @RequestMapping("/imagedelete")  
	 public ModelAndView resourceImageDelete(@RequestParam("id") long resrouceid, @RequestParam("img") long imgid){	
		 Resource resource = resourceRepository.find(resrouceid);
		 SecurityContext.assertCurrentUserMayEditThisResource(resource);
		 
		 resourceService.resourceImageDelete(resource, imgid);
		 return new ModelAndView("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
	 }

    
    @RequestMapping("/ajax/deleteTempImage")
    public ModelAndView deleteTempImage( 
            @RequestParam(value = "imageFileName") String imageFileName,
            @RequestParam(value = "resourceid") long id)  {

        Resource resource = resourceRepository.find(id);
        SecurityContext.assertCurrentUserMayEditThisResource(resource);

        String path = FileUtil.getGenFolderPath(currentEnvironment) + FileUtil.RESOURCE_SUB_FOLDER + FileUtil.RESOURCE_ORIGINAL_SUB_FOLDER;
        File tmpFile = new File(path, imageFileName);
        
        // Delete temporary file
        if (tmpFile.exists()) tmpFile.delete();

        return new ModelAndView("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }

    @RequestMapping("/displayPrintScreenHelpText")
    public ModelAndView displayPrintScreenHelpText(@RequestParam(value = "resourceid") long id) {
  
        Resource resource = resourceRepository.find(id);
        SecurityContext.assertCurrentUserMayEditThisResource(resource);

        return new ModelAndView("resourceimageprintscreenhelp", "resource", resource);
    }

}