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
   
   @RequestMapping(value = "/corps-humain")
   public ModelAndView exerciceDucorpshumain() {
       return prepareModelAndView("corps humain", "introtextcorpshumian.jsp");
   }
   
   
   @RequestMapping(value = "/exercices-de-math")
   public ModelAndView exerciceDeMath() {
       return prepareModelAndView("exercice math", "introexercicemath.jsp");
   }
   
   
   @RequestMapping(value = "/anglais-facile")
   public ModelAndView exerciceDanglais() {
       return prepareModelAndView("exercice anglais", "introtextanglaisfacile.jsp");
   }
   
   @RequestMapping(value = "/conjugaison")
   public ModelAndView exerciceDeconjugaisons() {
       return prepareModelAndView("exercice conjugaison", "introtextconjugaison.jsp");
   }
   
   @RequestMapping(value = "/ecole-virtuelle")
   public ModelAndView exerciceDecolevirtuelle() {
       return prepareModelAndView("exercice ecole virtuelle", "introtextecolevirtuelle.jsp");
   }
   
   @RequestMapping(value = "/orthographe")
   public ModelAndView exerciceDorthographe() {
       return prepareModelAndView("exercice d'orthographe", "introtextortographe.jsp");
   }
   
   @RequestMapping(value = "/nederlands")
   public ModelAndView exerciceNederlands() {
       return prepareModelAndView("nederlands", "introtextortographe.jsp");
   }

   private ModelAndView prepareModelAndView(String searchPhrase, String introJspName) {
       ModelAndView mv = new ModelAndView("landingpage");
       List<SearchResult> searchResults = searchService.search(searchPhrase);
       List<Resource> resourceList = searchService.getFilteredResources1FromSearchResults(searchResults, new SearchOptions());
       resourceListPagerService.setTokenListOfResources(mv, resourceList);

       mv.addObject("introtextjsp", introJspName);
       return mv;
   }
}
   


