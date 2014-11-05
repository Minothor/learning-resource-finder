package learningresourcefinder.controller;

import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResourceExpandController {

    @Autowired ResourceRepository resourcerepository ; 
    
    @RequestMapping("/ajax/expandresourceinfo")
    public ModelAndView getResourceExpandInfo(@RequestParam("resourceid") long resourceId) {
        Resource resource = resourcerepository.find(resourceId);
        return new ModelAndView("resourceexpand").addObject("resource",resource);

    }
}
