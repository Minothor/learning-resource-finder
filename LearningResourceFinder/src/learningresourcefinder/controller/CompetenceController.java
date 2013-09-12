package learningresourcefinder.controller;

import learningresourcefinder.model.Competence;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.NotificationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompetenceController extends BaseController<Competence> {

	@Autowired CompetenceRepository competenceRepository;
	
	@RequestMapping ("/tree")
	public ModelAndView DisplayCompTree (){
		ModelAndView mv= new ModelAndView("competencetree");
		return mv; 
	}

	@RequestMapping({"/competence/test"}) // out of context test of ajax query to add/edit/remove a competence in pgm tree
	public ModelAndView prepareModelAndView() {

		ModelAndView mv = new ModelAndView("testAjaxCompetence", "competence", null);

		return mv;
	}

	@RequestMapping(value="/ajax/competenceAddSubmit", method = RequestMethod.POST)
	public @ResponseBody String ajaxAddCompetence( @RequestParam("name") String nameCompetence, @RequestParam("code") String codeCompetence, @RequestParam("idparent") Long parentIdCompetence, @RequestParam("description") String descriptionCompetence){
		
		//checking

		//parent
		if (!competenceRepository.getIfParentExist(parentIdCompetence)) {
			return "La compétence parente n'existe pas. Faites-vous du URL hacking. Si vous êtes parvenu à ce message sans malice, il s'agit d'un bug. parentIdCompetence = "+ parentIdCompetence; 
		}
		// required code
		if (codeCompetence.equals("") || codeCompetence == null) {
			return "Le code ne peut être vide";
		}
		// unique code
		if (competenceRepository.getIfCodeExist(codeCompetence)) {
			return "Le code '"+codeCompetence+"' existe déjà dans une autre compétence. Chaque compétence doit avoir un code unique.";
		}
		
		Competence parent = getRequiredEntity(parentIdCompetence); 
		Competence competence = new Competence(codeCompetence,nameCompetence,parent,descriptionCompetence);
		SecurityContext.assertCurrentUserMayEditThisCompetence(competence);
		competence.setName(nameCompetence);
		competence.setCode(codeCompetence);
		competence.setDescription(descriptionCompetence);
		em.persist(competence);
		return "success";

	}

	@RequestMapping(value="/ajax/competenceRemoveSubmit", method = RequestMethod.POST)
	public @ResponseBody String ajaxRemoveCompetence( @RequestParam("id") Long idCompetence){
		
		Competence competence = getRequiredEntity(idCompetence);
		SecurityContext.assertCurrentUserMayEditThisCompetence(competence);
		//checking
		if (competence.getChildrenAndSubChildren().size() == 0){
			em.remove(competence);
			return "success";
		}else{
			return "La compétence possède une ou plusieurs compétences, veuillez d'abord les supprimer "; // We must return something (else the browser thinks it's an error case), but the value is not needed by our javascript code on the browser.
		}
	}
	
	@RequestMapping(value="/ajax/competenceEditSubmit", method = RequestMethod.POST)
	public @ResponseBody String ajaxEditCompetence( @RequestParam("name") String nameCompetence, @RequestParam("code") String codeCompetence, @RequestParam("description") String descriptionCompetence, @RequestParam("id") Long idCompetence){
		
		//checking
		if (competenceRepository.getIfCompetenceCodeExistOrIsCurrentlyBeingEdited(idCompetence,codeCompetence)) {
			Competence competence = getRequiredDetachedEntity(idCompetence);
			SecurityContext.assertCurrentUserMayEditThisCompetence(competence);
			competence.setName(nameCompetence);
			competence.setCode(codeCompetence);
			competence.setDescription(descriptionCompetence);
			em.merge(competence);
			return "success";
		}else{
			return "Le code existe déjà dans une autre compétence"; // We must return something (else the browser thinks it's an error case), but the value is not needed by our javascript code on the browser.
		}
 
	}
	
    @RequestMapping(value="/competencemovesubmit")
    public ModelAndView CompetenceMoveSubmit(@RequestParam ("hiddenFieldMoveCompetency") Long idCompetence, @RequestParam("codeField") String codeNewParent ){
        ModelAndView mv= new ModelAndView("competencetree"); 
        
        Competence competence=getRequiredEntity(idCompetence);
        Competence newParent =   competenceRepository.findByCode(codeNewParent);
        if (newParent==null){
            NotificationUtil.addNotificationMessage("Code parent inexistant"); 
            return mv;
        }
        
        if (competence.getParent() != null) {
            competence.getParent().getChildren().remove(competence);
        }
        newParent.getChildren().add(competence);
        competence.setParent(newParent); 
        competenceRepository.merge(competence);

        return mv; 
    }
	
	@RequestMapping(value="/ajax/competenceEditFillFields")
	public @ResponseBody CompetenceDataHolder competenceEditFillFields( @RequestParam("id") Long idCompetence){ 
		Competence competence = getRequiredEntity(idCompetence);
		SecurityContext.assertCurrentUserMayEditThisCompetence(competence);
		CompetenceDataHolder competenceDH = new CompetenceDataHolder();
		competenceDH.setCode(competence.getCode());
		competenceDH.setDescription(competence.getDescription());
		competenceDH.setName(competence.getName());
		return competenceDH; 
	} 

	//we use CompetenceDataHolder because spring cannot perform entities to jSON 	
	public static class CompetenceDataHolder {
		private String name;
		private String code;   // Short identifier that users can use to quickly refer a Competence
		private String description;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
}