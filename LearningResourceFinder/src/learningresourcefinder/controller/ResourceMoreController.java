package learningresourcefinder.controller;

import learningresourcefinder.service.ResourceListPager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResourceMoreController {

    @Autowired ResourceListPager resourceListPager;
    
    @RequestMapping("/ajax/getmoreresources")
    public ModelAndView getMoreResources(@RequestParam("tokenListOfResources") String tokenListOfResources) {
        return new ModelAndView("moreResources").addObject("resourceList", resourceListPager.getMoreResources(tokenListOfResources)).addObject("tokenListOfResources", tokenListOfResources);
    }
}