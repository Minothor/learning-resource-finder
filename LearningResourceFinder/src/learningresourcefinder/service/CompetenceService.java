package learningresourcefinder.service;

import java.util.ArrayList;
import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.web.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CompetenceService {

    @Autowired
    CompetenceRepository competenceRepository;

    // "Filters" the competence tree (into an list of Competences) to only
    // include the competences (and their childs/parents) related to a given
    // cycle.
    public List<Competence> computeAllCompetencesRelatedToCycle(Long cycleId,  List<Competence> competencesDirectlyAttachedToCycle) {
        List<Competence> competencesResult = new ArrayList<Competence>();
        
        for (Competence compFromCycle : competencesDirectlyAttachedToCycle) {

            // Go up through hierarchy until root
            Competence comp = compFromCycle.getParent();
            while (comp != null) {
                if (!competencesResult.contains(comp)) {
                    competencesResult.add(comp);
                }
                comp = comp.getParent();
            }
            competencesResult.add(compFromCycle);
        }
        return competencesResult;
    }
}
