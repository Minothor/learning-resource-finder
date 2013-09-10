package learningresourcefinder.util;

import learningresourcefinder.model.Competence;

public interface CompetencesTreeVisitor {

    void startCompetence(Competence competence);

    void endCompetence(Competence competence);

    void beforeChildren(int recurtionLevel);

    void afterChildren();

}
