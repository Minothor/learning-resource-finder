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

        		///////////// Assign Cycle
        		/// First, we check if we can display the dropdown to enable the user selecting another cycle for that competence.
        		boolean canDisplayCycleDropdown = true;  // If the parent has a cycle, the user should not set cycles to kids (it would be a non-sense to select another cycle than the parent).
        		// Has a parent a defined cycle?
        		Competence parent = competence.getParent();
        		while(parent != null) {
        			if (parent.getCycle() != null) {
        				canDisplayCycleDropdown = false;
        				break;
        			}
        			parent = parent.getParent();
        		}

        		// second, we generate the html
        		if (canDisplayCycleDropdown) {
        			if (competence.getCycle() != null) {
        				labelCycle = competence.getCycle().getName();
        			} else {
        				labelCycle = "Allouer un cycle";
        			}
        			htmlResult +="<span class=\"dropdown\">"
        					+"<span id=\"CP-"+ competence.getId() +"\" role=\"button\" data-toggle=\"dropdown\" data-target=\"#\" value=" + labelCycle + ">"
        					+ labelCycle + "<span class=\"caret\"></span>"
        					+"</span>";

        			// Items in the drop down menu
        			htmlResult += "<ul class=\"dropdown-menu\" role=\"menu\" aria-labelledby=\"dLabel\">";
        			for(Cycle c : lc){
        				htmlResult +="<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\"/competencetree\" id=" + "CY-" +c.getId() + "CP-"+ competence.getId() +">"+c.getName()+"</a></li>";
        			}
        			// "unassign" cycle choice
        			htmlResult +="<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\"/competencetree\" id=" + "CY-null" + "CP-"+ competence.getId() +">- aucun -</a></li>"; 
        			htmlResult += "</ul>"
        					+"</span>";
        		} else if (competence.getCycle() != null) { // Display non modifiable name.
        			htmlResult += competence.getCycle().getName();
        		}



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

        } else { // No privilege
        	 if (competence.getCycle() != null) { // Display non modifiable name.
     			htmlResult += competence.getCycle().getName();
     		}
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
