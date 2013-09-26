package learningresourcefinder.util;

import java.util.ArrayList;
import java.util.List;

import learningresourcefinder.model.Competence;

public class CompetenceNode {
    
    private Competence competence;
    private List<CompetenceNode> children = new ArrayList<CompetenceNode>();
    private CompetenceNode parent;
    
   
    public CompetenceNode(Competence competence, CompetenceNode parent){
        this.competence = competence;
        this.parent = parent;
    }
    

    public Competence getCompetence() {
        return competence;
    }

    public List<CompetenceNode> getChildren() {
        return children;
    }

}
