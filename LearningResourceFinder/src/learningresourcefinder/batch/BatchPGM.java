package learningresourcefinder.batch;

import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.util.CompetencesTreeVisitorImpl;
import learningresourcefinder.util.CompetencesTreeWalker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 *  BatchPGM Class For PGM/Matiere Test. 
 *  Author Ahmed Idoumhaidi.
 *  Group : Seb - Julien.
 */
@Transactional
@Service
public class BatchPGM implements Runnable {

    @Autowired
    CompetenceRepository mCompetenceRepository;

    private CompetencesTreeVisitorImpl competenceTreeVisitor;
    private CompetencesTreeWalker competenceTreeWalker;

    public static void main(String[] args) {

        BatchUtil.startSpringBatch(BatchPGM.class);

    }

    @Override
    public void run() {

        competenceTreeVisitor = new CompetencesTreeVisitorImpl();
        competenceTreeWalker = new CompetencesTreeWalker(mCompetenceRepository,
                competenceTreeVisitor);

        competenceTreeWalker.walk();

        System.out.println(competenceTreeVisitor.getHtmlResult());
    
    }

}
