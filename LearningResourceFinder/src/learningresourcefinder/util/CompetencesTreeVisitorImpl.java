package learningresourcefinder.util;

import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class CompetencesTreeVisitorImpl implements CompetencesTreeVisitor {

    private String htmlResult = "";



	@Override
    public void startCompetence(Competence competence,CycleRepository cycleRepository) {
        long id = competence.getId();
        List<Cycle> lc = cycleRepository.findAllCycles();
        String labelCycle;
        if(competence.getCycle()!=null)
        { labelCycle = competence.getCycle().getName();
        } else{ labelCycle = "Allouer un cycle";}
        htmlResult += "<li>" + " " + competence.getCode() + " " + competence.getName() + ">"
        +"<span class=\"dropdown\">"
        +"<a id=\"dLabel\" role=\"button\" data-toggle=\"dropdown\" data-target=\"#\" href=\"/page.html\">"
        + labelCycle + "<span class=\"caret\"></span>"
        +"</a>"
        +"<ul class=\"dropdown-menu\" role=\"menu\" aria-labelledby=\"dLabel\">";
        
        for(Cycle c : lc){
        	
        	htmlResult +="<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\"/tree\" id=" + "CY-" +c.getId() + "CP-"+ competence.getId() +"" +">"+c.getName()+"</a></li>";
        }

//        for(Cycle c : lc){
//        	htmlResult +="<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\"/ajax/setCycle\" id=\"C" + c.getId()+" idcomp="+ competence.getId() +">"+c.getName()+"</a></li>";
//        }
        htmlResult += "</ul>"
        +"</span>";
        
        // Main node hasn't parent, it must just be take like parent and only "Add" option is permit
        if (competence.getParent() != null){ htmlResult += "<a href=\"#\" " + "id='D-" + id + "'> Déplacer</a>";}
        htmlResult += "<a href=\"#\" " + "id=" + "\"" + "A-" + id + "\"" + ">" + " Ajouter" + "</a>";
        if (competence.getParent() != null){ htmlResult +="<a href=\"#\" " + "id=" + "\"" +  "E-" +id + "\"" + ">"+ " Éditer" +"</a>"
        +"<a href=\"#\" " + "id='R-" + id + "'>" + " Supprimer" + "</a>" 
        ;} 
        htmlResult += "";
     
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
