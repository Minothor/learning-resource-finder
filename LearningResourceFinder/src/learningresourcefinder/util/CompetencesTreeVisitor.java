package learningresourcefinder.util;

import learningresourcefinder.model.Competence;
import learningresourcefinder.repository.CycleRepository;

public interface CompetencesTreeVisitor {

    void startCompetence(Competence competence, int recurtionLevel);

    void endCompetence(Competence competence);

    void beforeChildren(int recurtionLevel);

    void afterChildren();

}
