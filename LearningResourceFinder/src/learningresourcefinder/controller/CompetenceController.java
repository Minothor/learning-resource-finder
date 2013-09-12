package learningresourcefinder.controller;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Competence;
import learningresourcefinder.security.SecurityContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompetenceController extends BaseController<Competence> {

	@RequestMapping ("/tree")
	public ModelAndView DisplayCompTree(){
		ModelAndView mv= new ModelAndView("competencetree");
		return mv; 
	}

	@RequestMapping({"/competence/test"}) // out of context test of ajax query to add a competence in pgm tree
	public ModelAndView prepareModelAndView() {

		ModelAndView mv = new ModelAndView("testAjaxCompetence", "competence", null);

		return mv;
	}

	@RequestMapping(value="/ajax/competenceAddSubmit", method = RequestMethod.POST)
	public @ResponseBody String ajaxAddCompetence( @RequestParam("name") String nameCompetence, @RequestParam("code") String codeCompetence, @RequestParam("idparent") Long parentIdCompetence){
		
		if (SecurityContext.canCurrentUserEditCompetence()){
			Competence parent = em.find(Competence.class, parentIdCompetence);
			Competence competence = new Competence(codeCompetence,nameCompetence,parent);
			em.persist(competence);
			return "OK : name " + nameCompetence  + " code " + codeCompetence + "in parent " + parent.getName();  // We must return something (else the browser thinks it's an error case), but the value is not needed by our javascript code on the browser.
		}
		else{
			return "KO";
		}


	}

	@RequestMapping(value="/ajax/competenceRemoveSubmit", method = RequestMethod.POST)
	public @ResponseBody String ajaxRemoveCompetence( @RequestParam("id") Long idCompetence){

		if (SecurityContext.canCurrentUserEditCompetence()){
			Competence competence = em.find(Competence.class, idCompetence);
			em.remove(competence);
			return "OK : id " + idCompetence;  // We must return something (else the browser thinks it's an error case), but the value is not needed by our javascript code on the browser.
		}else{
			return "KO";
		}

		
	}


}