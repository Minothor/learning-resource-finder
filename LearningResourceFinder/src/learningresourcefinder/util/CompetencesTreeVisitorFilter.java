package learningresourcefinder.util;

import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.repository.CycleRepository;


// builds another tree (of CompetenceNode, not of Competence directly) filtered to only include the nodes of the given list. 
public class CompetencesTreeVisitorFilter implements CompetencesTreeVisitor {

    List<Competence> competencesToInclude;
    CompetenceNode rootResult;
    
    
    @Override
    public void startCompetence(Competence competence, int recurtionLevel) {
        
    }
    
    
    @Override
    public void endCompetence(Competence competence) {
        // Empty
    }
    
    @Override
    public void beforeChildren(int recurtionLevel) {
        // Empty
    }
    @Override
    public void afterChildren() {
       // Empty
    }
    
    
    
    
}



