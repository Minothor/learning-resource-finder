package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.Resource;
import learningresourcefinder.service.ResourceListPagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResourceMoreController {

    @Autowired ResourceListPagerService resourceListPager;
    
    @RequestMapping("/ajax/getmoreresources")
    public ModelAndView getMoreResources(@RequestParam("tokenlistofresources") String tokenListOfResources) {
        List<Resource> listOfMoreResources = resourceListPager.getMoreResources(tokenListOfResources);
        
        // Set token to 0 when last resources to display 
        if (!resourceListPager.checkIfMoreResources(tokenListOfResources)) {
            tokenListOfResources = "0";
        }
        return new ModelAndView("moreresources").addObject("moreResourceList", listOfMoreResources).addObject("tokenMoreResources", tokenListOfResources);
    }
}