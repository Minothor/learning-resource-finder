package learningresourcefinder.controller;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.ComingSoonMail;
import learningresourcefinder.model.Competence;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.security.SecurityContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompController extends BaseController<BaseEntity> {
    
   @RequestMapping ("/tree")
   public ModelAndView DisplayCompTree (){
       ModelAndView mv= new ModelAndView("competencestree");
    return mv; 
   }
   
   @RequestMapping({"/competence/test"}) // out of context test of ajax query to add a competence in pgm tree
	public ModelAndView prepareModelAndView() {

		ModelAndView mv = new ModelAndView("testAjaxCompetence", "competence", null);
   	
		return mv;
	}
   
	@RequestMapping(value="/ajax/competenceAddSubmit", method = RequestMethod.POST)
	public @ResponseBody String ajaxAddCompetence( @RequestParam("name") String nameCompetence, @RequestParam("code") String codeCompetence){
		
		if (SecurityContext.canCurrentUserEditCompetence()){
			Competence competence = new Competence(codeCompetence,nameCompetence);
			em.persist(competence);
		}
		
		return "OK : name " + nameCompetence  + " code " + codeCompetence;  // We must return something (else the browser thinks it's an error case), but the value is not needed by our javascript code on the browser.
	}
   

}
