package learningresourcefinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import learningresourcefinder.model.Resource;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchResult;
import learningresourcefinder.service.ResourceListPagerService;
import learningresourcefinder.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LandingPageController {


    @Autowired SearchService searchService;
    @Autowired ResourceListPagerService resourceListPagerService;

    @RequestMapping(value = "/corps-humain")
    public ModelAndView exerciceCorpsHumain(HttpServletRequest request) {
        return prepareModelAndView("corps humain", request);
    }

    @RequestMapping(value = "/exercices-de-math")
    public ModelAndView exerciceDeMath(HttpServletRequest request) {
        return prepareModelAndView("math", request);
    }

    @RequestMapping(value = "/neerlandais")
    public ModelAndView Neerlandais(HttpServletRequest request) {
        return prepareModelAndView("neerlandais", request);
    }

    @RequestMapping(value = "/anglais-facile")
    public ModelAndView anglaisFacile(HttpServletRequest request) {
        return prepareModelAndView("anglais english", request);
    }

    @RequestMapping(value = "/conjugaison")
    public ModelAndView conjugaison(HttpServletRequest request) {
        return prepareModelAndView("conjugaison verbe", request);
    }

    @RequestMapping(value = "/orthographe")
    public ModelAndView orthographe(HttpServletRequest request) {
        return prepareModelAndView("orthographe", request);
    }

    @RequestMapping(value = "/ecole-virtuelle")
    public ModelAndView ecoleVirtuelle(HttpServletRequest request) {
        return prepareModelAndView("ecole", request);
    }

    @RequestMapping(value = "/nederlands")
    public ModelAndView nederlands(HttpServletRequest request) {
        return prepareModelAndView("neerlandais", request);
    }

    @RequestMapping(value = "/chien-et-chat")
    public ModelAndView chienEtChat(HttpServletRequest request) {
        return prepareModelAndView("chien chat", request);
    }

    @RequestMapping(value = "/subjonctif")
    public ModelAndView subjonctif(HttpServletRequest request) {
        return prepareModelAndView("subjonctif", request);
    }

    @RequestMapping(value = "/ligne-du-temps")
    public ModelAndView ligneDuTemps(HttpServletRequest request) {
        return prepareModelAndView("ligne temps", request);
    }

    @RequestMapping(value = "/botanique")
    public ModelAndView botanique(HttpServletRequest request) {
        return prepareModelAndView("botanique", request);
    }



    private ModelAndView prepareModelAndView(String searchPhrase, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("landingpage");
        List<SearchResult> searchResults = searchService.search(searchPhrase);
        List<Resource> resourceList = searchService.getFilteredResources1FromSearchResults(searchResults, new SearchOptions());
        resourceListPagerService.setTokenListOfResources(mv, resourceList);

        String urlPath = (String)request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE); // http://stackoverflow.com/questions/3796545/how-do-i-get-the-requestmapping-value-in-the-controller
        System.out.println(urlPath);
        mv.addObject("urlPath", urlPath); 
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


