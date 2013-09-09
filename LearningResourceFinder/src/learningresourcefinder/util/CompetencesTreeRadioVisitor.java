package learningresourcefinder.util;
import learningresourcefinder.model.Competence;

public class CompetencesTreeRadioVisitor implements CompetencesTreeVisitor{
    
    String htmlResult="";
    Competence CompetenceFromRequest;
    
    public CompetencesTreeRadioVisitor(Competence competence){
        this.CompetenceFromRequest = competence;
    }
    
    @Override
    public void startArticle(Competence competence) {
        // TODO Auto-generated method stub
              htmlResult+="<li>" + competence.getName();
    }

    @Override
    public void endArticle(Competence competence) {
        // TODO Auto-generated method stub
        htmlResult+="</li>";
        
    }

    @Override
    public void beforeChildren(int recurtionLevel) {
        // TODO Auto-generated method stub
        htmlResult += "<ul class=\"articletreelevel"+recurtionLevel+"\">";
        
    }

    @Override
    public void afterChildren() {
        // TODO Auto-generated method stub
        htmlResult += "</ul>";
        
    }

    public String getHtmlResult(){
        return htmlResult;
    }
}
