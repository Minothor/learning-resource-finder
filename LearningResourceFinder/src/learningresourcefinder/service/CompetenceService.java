package learningresourcefinder.service;

import java.util.ArrayList;
import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CompetenceService {
   
    public void computeAllCompetencesRelatedToCycle(Cycle cycle){
        List<Competence> competencesResult = new ArrayList<Competence>();
        List<Competence> competencesDirectlyAttachedToCycle = cycle.getCompetences();
        
        for(Competence compFromCycle : competencesDirectlyAttachedToCycle){

            // Go up through hierarchy until root
            Competence comp = compFromCycle.getParent();
            while(comp != null){
                if(!competencesResult.contains(comp)){
                    competencesResult.add(comp);   
                }
                comp = comp.getParent();
            }
            competencesResult.add(compFromCycle);
        }
        
        cycle.setComputedCompetences(competencesResult);
        
    }

//    // Rebuilds the cache of Cycle.computedCompetences
//    public void computeAllCompetencesRelatedToCycle(Cycle cycle) {
//        List<Competence> competencesResult = new ArrayList<Competence>();
//
//        List<Competence> competencesDirectlyAttachedToCycle = cycle.getCompetences();
//        for (Competence comp : competencesDirectlyAttachedToCycle) {
//            gatherAllParents(comp.getParent(), competencesResult);  
//            competencesResult.add(comp);
//        }
//        
//        cycle.setComputedCompetences(competencesResult);        
//    }
//
//    // This method is implemented with a false recursivity.
//    private void gatherAllParents(Competence Competenceparent,List<Competence>  competencesResult) {
//        if (Competenceparent != null && !competencesResult.contains(Competenceparent)) {
//            gatherAllParents(Competenceparent.getParent(),competencesResult); // False recursivity (could be a simple loop)
//            competencesResult.add(Competenceparent);
//        }
//    }
    

}
