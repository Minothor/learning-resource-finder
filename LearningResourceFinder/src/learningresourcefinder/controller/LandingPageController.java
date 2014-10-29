package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.Resource;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchResult;
import learningresourcefinder.service.ResourceListPagerService;
import learningresourcefinder.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LandingPageController {
    
    
    @Autowired SearchService searchService;
    @Autowired ResourceListPagerService resourceListPagerService;
    
   @RequestMapping(value = "/exercices-de-neerlandais")
   public ModelAndView exerciceDeNeerlandais() {
       return prepareModelAndView("exercice neerlandais", "introexerciceneerlandais.jsp");
   }
   
   @RequestMapping(value = "/exercices-de-math")
   public ModelAndView exerciceDeMath() {
       return prepareModelAndView("exercice math", "introexercicemath.jsp");
   }

   private ModelAndView prepareModelAndView(String searchPhrase, String introJspName) {
       ModelAndView mv = new ModelAndView("landingpage");
       List<SearchResult> searchResults = searchService.search(searchPhrase);
       List<Resource> resourceList = searchService.getFilteredResources1FromSearchResults(searchResults, new SearchOptions());
       resourceListPagerService.setTokenListOfResources(mv, resourceList);

       mv.addObject("introtextjsp", introJspName);
       return mv;
   }

   
//   @RequestMapping(value = "introtextexercices-de-math")
//   public ModelAndView exerciceDeMath() {
//       resourcelist = search("introtextexercice-de-math");
//        ModelAndView mv = new ModelAndView("landingpage");
//        mv.addObject("introtextjsp", "introexercicemath.jsp");
//        return mv;
//   }
   
//   @RequestMapping(value = "/introtextconjugaison")
//   public ModelAndView exerciceDeconjugaison() {
//       resourcelist = search("introtextconjugaison");
//       ResourceListPagerService.preparedMV(mv);
//       
//        ModelAndView mv = new ModelAndView("landingpage");
//        mv.addObject("introtextjsp", "/WEB-INF/include/conjugaison.jsp");
//        return mv;
//   }
//   
//   @RequestMapping(value = "/introtextorthographe")
//   public ModelAndView exerciceDorthographe() {
//       resourcelist = search("introtextorthographe");
//        ModelAndView mv = new ModelAndView("landingpage");
//        mv.addObject("introtextjsp", "introtextorthographe.jsp");
//        return mv;
//   }
//   
//   @RequestMapping(value = "/introtextanglais-facile")
//   public ModelAndView exerciceDanglaisfacile() {
//       resourcelist = search("introtextanglais-facile");
//        ModelAndView mv = new ModelAndView("landingpage");
//        mv.addObject("introtextjsp", "/WEB-INF/include/introtextanglaisfacile.jsp");
//        return mv;
//   }
//   
//   @RequestMapping(value = "/introtextecole-virtuel")
//   public ModelAndView exerciceDecolevirtuel() {
//       resourcelist = search("introtextecole-virtuel");
//        ModelAndView mv = new ModelAndView("landingpage");
//        mv.addObject("introtextjsp", "/WEB-INF/include/introtextecolevirtuelle.jsp");
//        return mv;
//   }
//   
//   @RequestMapping(value = "/introtextcorps-humain")
//   public ModelAndView exerciceDecorpshumain() {
//       resourcelist = search("introtextcorps-humain");
//        ModelAndView mv = new ModelAndView("landingpage");
//        mv.addObject("introtextjsp", "/WEB-INF/include/introtextcorpshumain.jsp");
//        return mv;
//   }
 }


