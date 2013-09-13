package learningresourcefinder.util;

import learningresourcefinder.model.Competence;

public class CompetencesTreeVisitorImpl implements CompetencesTreeVisitor {


    private String htmlResult = "";


    @Override
    
    public void startCompetence(Competence competence) {
        long id = competence.getId();
        htmlResult += "<li>" + " " + competence.getCode() + " " + competence.getName() + "<font size=\"1\">"
        +"<a href=\"#\" " + "id='D-" + id + "'> Déplacer</a>"
        +"<a href=\"#\" " + "id=" + "\"" + "A-" + id + "\"" + ">" + " Ajouter" + "</a>"
        +"<a href=\"#\" " + "id=" + "\"" +  "E-" +id + "\"" + ">"+ " Éditer" +"</a>"
        +"<a href=\"#\" " + "id='R-" + id + "'>" + " Supprimer" + "</a>" 
        +"</font>"; 
     
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
