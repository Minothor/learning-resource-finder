package learningresourcefinder.util;

import learningresourcefinder.model.Competence;

public interface CompetencesTreeVisitor {

    void startArticle(Competence competence);

    void endArticle(Competence competence);

    void beforeChildren(int recurtionLevel);

    void afterChildren();

}
