package learningresourcefinder.util;

import learningresourcefinder.model.Competence;
import learningresourcefinder.repository.CycleRepository;

public interface CompetencesTreeVisitor {

    void startCompetence(Competence competence,CycleRepository cycleRepository);

    void endCompetence(Competence competence);

    void beforeChildren(int recurtionLevel);

    void afterChildren();

}
