package learningresourcefinder.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.CycleRepository;

/** Performs a recursive walk of a tree (depth first), calling methods of a given CompetenceTreeVisitor object at each node */
public class CompetencesTreeWalker {
    
    private CompetencesTreeVisitorImpl  mCompetenceTreeVisitor;
    private CompetenceRepository mCompetenceRepository;
    private Competence competenceFromRequest;
    private int currentLevel;
	private CycleRepository mCycleRepository; 

	
    public CompetencesTreeWalker(CompetencesTreeVisitorImpl competenceTreeVisitor, Competence competenceParent, CompetenceRepository competenceRepository, CycleRepository mCycleRepository){
        this.mCompetenceRepository = competenceRepository;     
        this.mCompetenceTreeVisitor = competenceTreeVisitor;
        this.competenceFromRequest = competenceParent;
        this.mCycleRepository = mCycleRepository;
    }
        
	public void walk() {
        List<Competence> listOfCompetences = null;
        
        if(competenceFromRequest == null){
            listOfCompetences = new ArrayList<Competence>();
            listOfCompetences.add(mCompetenceRepository.findRoot());
            
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
        mCompetenceTreeVisitor.startCompetence(competence,mCycleRepository);
        currentLevel++;
        processCompetencesList(competence.getChildren());
        currentLevel--;
        mCompetenceTreeVisitor.endCompetence(competence);
    }

}
