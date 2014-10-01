package learningresourcefinder.util;

import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.security.Privilege;
import learningresourcefinder.security.SecurityContext;

public class CompetencesTreeVisitorImpl implements CompetencesTreeVisitor {

    private String htmlResult = "";

    CycleRepository cycleRepository;
    
    public CompetencesTreeVisitorImpl(CycleRepository aCycleRepository) {
    	this.cycleRepository = aCycleRepository;
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
