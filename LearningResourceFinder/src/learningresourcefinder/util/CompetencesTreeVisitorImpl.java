package learningresourcefinder.util;

import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.security.Privilege;
import learningresourcefinder.security.SecurityContext;

public class CompetencesTreeVisitorImpl implements CompetencesTreeVisitor {

    private String htmlResult = "";

    CycleRepository cycleRepository;
    ResourceRepository resourceRepository;
    
    public CompetencesTreeVisitorImpl(CycleRepository aCycleRepository, ResourceRepository aResourceRepository) {
    	this.cycleRepository = aCycleRepository;
    	this.resourceRepository = aResourceRepository;
    }

	@Override
    public void startCompetence(Competence competence, int recurtionLevel) {
        long id = competence.getId();
        List<Cycle> lc = cycleRepository.findAllCycles();
        String labelCycle;


        htmlResult += "<li>" + " " + competence.getCode() + " - <a href='/searchresource?competenceid=" + competence.getId() + "'>" + competence.getName() + "</a> ";

        if(SecurityContext.isUserHasPrivilege(Privilege.MANAGE_COMPETENCE)){
    		htmlResult += "<small>";
        	if (competence.getParent() != null){ // not the main node

        		/////////// Move node
        		// Main node hasn't parent, it must just be take like parent and only "Add" option is permit
        		htmlResult += " <span class='clickable' href=\"#\" " + "id='D-" + id + "'>Déplacer</span>";
        	}

        	//////////////// Add, Edit, Delete node 
        	htmlResult += " <span class='clickable' href=\"#\" " + "id=" + "\"" + "A-" + id + "\"" + ">" + "Ajouter" + "</span>";
        	if (competence.getParent() != null){ 
        	    htmlResult +=" <span class='clickable' href=\"#\" " + "id=" + "\"" +  "E-" +id + "\"" + ">"+ "Éditer" +"</span>";

        	    if (competence.getChildren().size() == 0) {
        	        htmlResult += " <span class='clickable' href=\"#\" " + "id='R-" + id + "'>" + "Supprimer" + "</span>";
        	    }
        	}
        	
        	// TODO Temporary coding to display cycle(s) where either no resource(s) associated to category or resource without cycle ! 
        	for (Cycle cycle : lc) {
        	    if (resourceRepository.countResourcesByCompetenceAndCycle(competence.getId(), cycle.getId()) == 0) {
        	        htmlResult += " " + cycle.getName();
        	    }
            }
        	htmlResult += "</small>";

        }
	} 

	@Override
	public void endCompetence(Competence competence) {
		htmlResult +="</a>";
		htmlResult += "</li>";

	}

	@Override
	public void beforeChildren(int recurtionLevel) {
		htmlResult += "<ul>";

	}

	@Override
	public void afterChildren() {
		htmlResult += "</ul>";

	}

	public String getHtmlResult() {
		return htmlResult;
	}


}
