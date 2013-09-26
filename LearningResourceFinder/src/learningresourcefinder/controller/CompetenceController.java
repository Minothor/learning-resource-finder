package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;

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
	@Autowired IndexManagerService indexManager;
	
	@RequestMapping ("/competencetree")
	// Parameter rootCode defines the node from which we display the tree (we display all if null)
	public ModelAndView competenceTree (@RequestParam(value="rootCode", required=false) String rootCode) {
		ModelAndView mv= new ModelAndView("competencetree");
		Competence root;
		if (rootCode != null) {
			root = competenceRepository.findByCode(rootCode);
		} else {
			root = competenceRepository.findRoot();
		}
		mv.addObject("root", root);
		return mv; 
	}


	@RequestMapping(value="/ajax/competenceaddsubmit")
	public @ResponseBody String competenceAddSubmit( @RequestParam("name") String nameCompetence, @RequestParam("code") String codeCompetence, @RequestParam("idparent") Long parentIdCompetence, @RequestParam("description") String descriptionCompetence){
		
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
		indexManager.add(competence);
		NotificationUtil.addNotificationMessage("Compétence ajoutée.",Status.SUCCESS); 
		return "success";

	}

	@RequestMapping(value="/ajax/competenceremovesubmit")
	public @ResponseBody String ajaxRemoveCompetence( @RequestParam("id") Long idCompetence){
		
		Competence competence = getRequiredEntity(idCompetence);
		SecurityContext.assertCurrentUserMayEditThisCompetence(competence);
		//checking
        if (competence.getParent()==null){
            return "Noeud principal, ne peut être déplacé!";
        }
		if (competence.getChildrenAndSubChildren().size() == 0){
			em.remove(competence);
			indexManager.delete(competence);
			NotificationUtil.addNotificationMessage("Compétence supprimée.",Status.SUCCESS); 
			return "success";
		}else{
			return "La compétence possède une ou plusieurs compétences, veuillez d'abord les supprimer "; // We must return something (else the browser thinks it's an error case), but the value is not needed by our javascript code on the browser.
		}
	}
	
	@RequestMapping(value="/ajax/competenceeditsubmit")
	public @ResponseBody String ajaxEditCompetence( @RequestParam("name") String nameCompetence, @RequestParam("code") String codeCompetence, @RequestParam("description") String descriptionCompetence, @RequestParam("id") Long idCompetence){
		//checking
		if (competenceRepository.getIfCompetenceCodeExistOrIsCurrentlyBeingEdited(idCompetence,codeCompetence)) {
			Competence competence = getRequiredDetachedEntity(idCompetence);
			SecurityContext.assertCurrentUserMayEditThisCompetence(competence);
	        if (competence.getParent()==null){
	            return "Noeud principal, ne peut être déplacé!";
	        }
			competence.setName(nameCompetence);
			competence.setCode(codeCompetence);
			competence.setDescription(descriptionCompetence);
			em.merge(competence);
			indexManager.update(competence);
			NotificationUtil.addNotificationMessage("Compétence éditée.",Status.SUCCESS); 
			return "success";
		} else {
			return "Le code existe déjà dans une autre compétence"; // We must return something (else the browser thinks it's an error case), but the value is not needed by our javascript code on the browser.
		}
 
	}
	
    @RequestMapping(value="/competencemovesubmit")
    public ModelAndView CompetenceMoveSubmit(@RequestParam ("hiddenFieldMoveCompetency") Long idCompetence, @RequestParam("codeField") String codeNewParent ){
        ModelAndView mv= new ModelAndView("competencetree"); 
        
        Competence competence=getRequiredEntity(idCompetence);
        SecurityContext.assertCurrentUserMayEditThisCompetence(competence);
        Competence newParent =   competenceRepository.findByCode(codeNewParent);
        
        
        /////////// Can we move that node?
        if (competence.getParent()==null) {  // defensive coding
            NotificationUtil.addNotificationMessage("La racine ne peut être déplacée.",Status.ERROR); 
            return mv;
        }
        if (newParent==null){  // defensive coding
            NotificationUtil.addNotificationMessage("Code parent inexistant",Status.ERROR); 
            return mv;
        }
        if (competence.getChildrenAndSubChildren().contains(newParent)){
        	NotificationUtil.addNotificationMessage("Une compétence ne peut être déplacée dans ses propres sous-compétences",Status.ERROR);
        	return mv;
        }
        if (competence.getId() == newParent.getId()){
        	NotificationUtil.addNotificationMessage("Une compétence ne peut être déplacée sur elle même",Status.ERROR);
        	return mv;
        }
        /// Has the new parent (or his parent), a cycle assigned which is different?
        if (competence.getCycle() != null) {
        	Competence parent = newParent;
        	while(parent!=null){
        		if(parent.getCycle() != null && !competence.getCycle().equals(parent.getCycle())){
        			NotificationUtil.addNotificationMessage("Le cycle ("+parent.getCycle()+") lié à un des parents ("+parent.getFullName()+") ne peut pas être différent du cycle ("+competence.getCycle()+") de la compétence que vous déplacez.",Status.ERROR);
        			return mv;
        		}
        		parent = parent.getParent();
        	}
        }

        
        ////////////// OK, we move the node.
        if (competence.getParent() != null) {
            competence.getParent().getChildren().remove(competence);
        }
        newParent.getChildren().add(competence);
        competence.setParent(newParent); 
        competenceRepository.merge(competence);
        NotificationUtil.addNotificationMessage("Compétence déplacée.",Status.SUCCESS); 
        return mv; 
    }
	
	@RequestMapping(value="/ajax/competenceeditfillfields")
	public @ResponseBody CompetenceDataHolder competenceEditFillFields( @RequestParam("id") Long idCompetence){ 
		Competence competence = getRequiredEntity(idCompetence);
		SecurityContext.assertCurrentUserMayEditThisCompetence(competence);
		CompetenceDataHolder competenceDH = new CompetenceDataHolder();
		competenceDH.setCode(competence.getCode());
		competenceDH.setDescription(competence.getDescription());
		competenceDH.setName(competence.getName());
		return competenceDH; 
	} 
	
	@RequestMapping(value="/ajax/setCycle")
	public @ResponseBody String setCycle(@RequestParam("idcomp") Long idCompetence, @RequestParam("idcycle") Long idCycle){ 
		Competence competence = getRequiredEntity(idCompetence);
		Cycle cycle = (Cycle)getRequiredEntity(idCycle, Cycle.class);
		
		//Verify if children, sub children or parents have a different cycle
		List<Competence> childrenAndSubChildren = competence.getChildrenAndSubChildren();
		for(Competence cptChild : childrenAndSubChildren){
			if(cptChild.getCycle() != null  && ! cycle.equals(cptChild.getCycle())){
				return "Une des sous-compétences ("+cptChild.getFullName()+") est déjà assignée à un cycle différent, ce qui n'est pas logique. Veuillez d'abord changer les sous-compétence de cycle afin que des enfants ne soient pas en contradiction avec leurs parents.";
			}
		}
		
		// Has one of the parent a different cycle ?
		Competence parent = competence.getParent();
		while(parent != null){
			if(parent.getCycle() != null  &&  !cycle.equals(parent.getCycle())){
				return "Une des compétences parente ("+parent.getFullName()+") est déjà assignée à un cycle différent, ce qui n'est pas logique. Veuillez d'abord changer le parent de cycle afin que des enfants ne soient pas en contradiction avec leurs parents.";
			}
			parent = parent.getParent();
		}
		
		
		//FIXME DECOMMENT IN PROD : SecurityContext.assertCurrentUserMayEditThisCompetence(competence);
		competence.setCycle(cycle);
		em.merge(competence);
		return "success";
	} 

	//we use CompetenceDataHolder because spring cannot does not convert our entities to jSON correctly. 	
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