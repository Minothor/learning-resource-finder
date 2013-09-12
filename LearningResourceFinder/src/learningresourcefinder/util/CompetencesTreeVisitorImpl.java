package learningresourcefinder.util;

import learningresourcefinder.model.Competence;

public class CompetencesTreeVisitorImpl implements CompetencesTreeVisitor {


    private String htmlResult = "";


    @Override
    
    public void startCompetence(Competence competence) {
        long id = competence.getId();
        htmlResult += "<li>" + competence.getName()
        +"<a href=\"javascript:ajaxCompetenceRemoveSubmit([ici l'id à supprimer])\" " + "id='S-" + id + "'>" + "supprimer" + "</a>" 
        +"<a data-toggle =\"modal\" href=\"#myModal\" " + "id='D-" + id + "'>déplacer</a>"
        +"<a href=\"...\" " + "id=" + "\"" +  "E-" +id + "\"" + ">"+ "éditer" +"</a>" 
        +"<a href=\"javascript:ajaxCompetenceAddSubmit([ici l'id du parent])\" " + "id=" + "\"" + "A-" + id + "\"" + ">" + "ajouter" + "</a>"; 
     
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
