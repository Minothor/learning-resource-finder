package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;
import learningresourcefinder.util.HTMLUtil;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
			return "La catégorie parente n'existe pas. Faites-vous du URL hacking. Si vous êtes parvenu à ce message sans malice, il s'agit d'un bug. parentIdCompetence = "+ parentIdCompetence; 
		}
		// required code
		if (codeCompetence.equals("") || codeCompetence == null) {
			return "Le code ne peut être vide";
		}
		// unique code
		if (competenceRepository.getIfCodeExist(codeCompetence)) {
			return "Le code '"+codeCompetence+"' existe déjà dans une autre catégorie. Chaque catégorie doit avoir un code unique.";
		}
		
		Competence parent = getRequiredEntity(parentIdCompetence); 
		Competence competence = new Competence(codeCompetence,nameCompetence,parent,descriptionCompetence);
		SecurityContext.assertCurrentUserMayEditThisCompetence();
		competence.setName(nameCompetence);
		competence.setCode(codeCompetence);
		competence.setDescription(descriptionCompetence);
		em.persist(competence);
		indexManager.add(competence);
		NotificationUtil.addNotificationMessage("Catégorie ajoutée.",Status.SUCCESS); 
		return "success";

	}

	@RequestMapping(value="/ajax/competenceremovesubmit")
	public @ResponseBody String ajaxRemoveCompetence( @RequestParam("id") Long idCompetence){
		
		Competence competence = getRequiredEntity(idCompetence);
		SecurityContext.assertCurrentUserMayEditThisCompetence();
		//checking
        if (competence.getParent()==null){
            return "Noeud principal, ne peut être déplacé!";
        }
		if (competence.getChildrenAndSubChildren().size() == 0){
			em.remove(competence);
			indexManager.delete(competence);
			NotificationUtil.addNotificationMessage("Catégorie supprimée.",Status.SUCCESS); 
			return "success";
		}else{
			return "La catégorie possède une ou plusieurs catégories, veuillez d'abord les supprimer "; // We must return something (else the browser thinks it's an error case), but the value is not needed by our javascript code on the browser.
		}
	}
	
	@RequestMapping(value="/ajax/competenceeditsubmit")
	public @ResponseBody String competenceEditSubmit( @RequestParam("name") String nameCompetence, @RequestParam("code") String codeCompetence, @RequestParam("description") String descriptionCompetence, @RequestParam("id") Long idCompetence){
	    codeCompetence = codeCompetence == null ? null : codeCompetence.trim();
	    if (StringUtils.isBlank(codeCompetence)) {
	        return "Chaque catégorie doit avoir un code";
	    }
		if (competenceRepository.isThereAnotherCompetenceWithThatCode(idCompetence,codeCompetence)) {
			Competence competence = getRequiredDetachedEntity(idCompetence);
			if (! SecurityContext.canCurrentUserEditCompetence()) {  // May happen with session time out
			    return "Problème de sécurité: êtes-vous encore connecté avec votre compte utilisateur?";
			}
			competence.setName(nameCompetence);
			competence.setCode(codeCompetence);
			competence.setDescription(descriptionCompetence);
			em.merge(competence);
			indexManager.update(competence);
			NotificationUtil.addNotificationMessage("Catégorie éditée.",Status.SUCCESS); 
			return "success";
		} else {
			return "Ce code est déjà pris par une autre catégorie"; 
		}
 
	}
	
    @RequestMapping(value="/competencemovesubmit")
    public ModelAndView competenceMoveSubmit(@RequestParam ("hiddenFieldMoveCompetency") Long idCompetence, @RequestParam("codeField") String codeNewParent ){
        ModelAndView mv= new ModelAndView("competencetree"); 
        
        Competence competence=getRequiredEntity(idCompetence);
        SecurityContext.assertCurrentUserMayEditThisCompetence();
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
        	NotificationUtil.addNotificationMessage("Une catégorie ne peut être déplacée dans ses propres sous-catégories",Status.ERROR);
        	return mv;
        }
        if (competence.getId() == newParent.getId()){
        	NotificationUtil.addNotificationMessage("Une catégorie ne peut être déplacée sur elle même",Status.ERROR);
        	return mv;
        }
        /// Has the new parent (or his parent), a cycle assigned which is different?
        if (competence.getCycle() != null) {
        	Competence parent = newParent;
        	while(parent!=null){
        		if(parent.getCycle() != null && !competence.getCycle().equals(parent.getCycle())){
        			NotificationUtil.addNotificationMessage("Le cycle ("+parent.getCycle()+") lié à un des parents ("+parent.getFullName()+") ne peut pas être différent du cycle ("+competence.getCycle()+") de la catégorie que vous déplacez.",Status.ERROR);
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
        NotificationUtil.addNotificationMessage("Catégorie déplacée.",Status.SUCCESS); 
        return mv; 
    }
	
	@RequestMapping(value="/ajax/competenceeditfillfields")
	public @ResponseBody CompetenceDataHolder competenceEditFillFields( @RequestParam("id") Long idCompetence){ 
		Competence competence = getRequiredEntity(idCompetence);
		SecurityContext.assertCurrentUserMayEditThisCompetence();
		CompetenceDataHolder competenceDH = new CompetenceDataHolder();
		competenceDH.setCode(competence.getCode());
		competenceDH.setDescription(competence.getDescription());
		competenceDH.setName(competence.getName());
		return competenceDH; 
	} 
	
	@RequestMapping(value="/ajax/setcycle")
	public @ResponseBody String setCycle(@RequestParam("idcomp") Long idCompetence, @RequestParam("idcycle") String idCycleStr){ 
		Competence competence = getRequiredEntity(idCompetence);

		SecurityContext.assertCurrentUserMayEditThisCompetence();
		
		Cycle cycle;
		if (idCycleStr==null || "null".equals(idCycleStr)) {
		    cycle = null;
		    
		} else {
	        cycle = (Cycle)getRequiredEntity(Long.parseLong(idCycleStr), Cycle.class);
	        
	        //Verify if children, sub children or parents have a different cycle
	        List<Competence> childrenAndSubChildren = competence.getChildrenAndSubChildren();
	        for(Competence cptChild : childrenAndSubChildren){
	            if(cptChild.getCycle() != null  && ! cycle.equals(cptChild.getCycle())){
	                return "Une des sous-catégories ("+cptChild.getFullName()+") est déjà assignée à un cycle différent, ce qui n'est pas logique. Veuillez d'abord changer les sous-catégories de cycle afin que des enfants ne soient pas en contradiction avec leurs parents.";
	            }
	        }
	        
	        // Has one of the parent a different cycle ?
	        Competence parent = competence.getParent();
	        while(parent != null){
	            if(parent.getCycle() != null  &&  !cycle.equals(parent.getCycle())){
	                return "Une des catégories parente ("+parent.getFullName()+") est déjà assignée à un cycle différent, ce qui n'est pas logique. Veuillez d'abord changer le parent de cycle afin que des enfants ne soient pas en contradiction avec leurs parents.";
	            }
	            parent = parent.getParent();
	        }
		}
		
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
			this.name = HTMLUtil.removeHtmlTags(name);
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = HTMLUtil.removeHtmlTags(code);
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description =  HTMLUtil.removeHtmlTags(description);
		}
	}
}