package learningresourcefinder.util;

import java.util.Collection;
import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.CycleRepository;

/** Performs a recursive walk of a tree (depth first), calling methods of a given CompetenceTreeVisitor object at each node */
public class CompetencesTreeWalker {
    
    private CompetencesTreeVisitor  mCompetenceTreeVisitor;
    private CompetenceRepository mCompetenceRepository;
    private Competence competenceFromRequest;
    private int currentLevel;
	
    public CompetencesTreeWalker(CompetenceRepository competenceRepository,CompetencesTreeVisitor competenceTreeVisitor) {
        this(competenceRepository,competenceTreeVisitor,null);
    }
 
    public CompetencesTreeWalker(CompetenceRepository competenceRepository, CompetencesTreeVisitor competenceTreeVisitor,CycleRepository mCycleRepository) {
        this(competenceRepository,competenceTreeVisitor,null,mCycleRepository);
    }
    
    public CompetencesTreeWalker(CompetenceRepository competenceRepository, CompetencesTreeVisitor competenceTreeVisitor,Competence competenceParent,CycleRepository mCycleRepository){
        this.mCompetenceRepository = competenceRepository;     
        this.mCompetenceTreeVisitor = competenceTreeVisitor;
        this.competenceFromRequest = competenceParent;
    }
        
	public void walk() {
        List<Competence> listOfCompetences = null;
        
        if(competenceFromRequest == null){
            listOfCompetences = mCompetenceRepository.findAllWithoutParent();
            
        } else {
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
        mCompetenceTreeVisitor.startCompetence(competence, currentLevel);
        currentLevel++;
        processCompetencesList(competence.getChildren());
        currentLevel--;
        mCompetenceTreeVisitor.endCompetence(competence);
    }

}
