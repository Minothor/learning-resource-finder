package learningresourcefinder.util;

import java.util.Collection;
import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.repository.CompetenceRepository;

public class CompetencesTreeWalker {
    
    private CompetencesTreeRadioVisitor mCompetenceTreeVisitor;
    private CompetenceRepository mCompetenceRepository;
    private Competence competenceFromRequest;
    private int currentLevel; 

    public CompetencesTreeWalker(CompetenceRepository competenceRepository,CompetencesTreeRadioVisitor competenceTreeVisitor) {
        this(competenceRepository,competenceTreeVisitor,null);
    }
   
    public CompetencesTreeWalker(CompetenceRepository competenceRepository,CompetencesTreeRadioVisitor competenceTreeVisitor,Competence competenceParent){
        this.mCompetenceRepository = competenceRepository;     
        this.mCompetenceTreeVisitor = competenceTreeVisitor;
        this.competenceFromRequest = competenceParent;
    }
    
    public void walk() {
        
        List<Competence> listOfCompetences = null;
        
        if(competenceFromRequest == null){
            
            // competenceFromRequest = mCompetenceRepository. // Find all competences without parent.
            listOfCompetences = mCompetenceRepository.findAllWithoutParent();
            
        }else{
            listOfCompetences = competenceFromRequest.getChildren();
        }
        currentLevel = 0;
        processCompetencesList(listOfCompetences);
        
    }

    public void processCompetencesList(Collection<Competence> listOfCompetences) {
        if(listOfCompetences.size() > 0){
            mCompetenceTreeVisitor.beforeChildren(currentLevel);
            for(Competence competence : listOfCompetences)
                processCompetences(competence);
            mCompetenceTreeVisitor.afterChildren();
        }
        

    }

    public void processCompetences(Competence competence) {

        mCompetenceTreeVisitor.startArticle(competence);
        currentLevel++;
        processCompetencesList(competence.getChildren());
        currentLevel--;
        mCompetenceTreeVisitor.endArticle(competence);
        
        
    }

}
