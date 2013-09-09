package learningresourcefinder.batch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import learningresourcefinder.model.Competence;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.util.CompetencesTreeRadioVisitor;
import learningresourcefinder.util.CompetencesTreeWalker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *  BatchPGM Class For PGM/Matiere Test. 
 *  Author Ahmed Idoumhaidi.
 *  Group : Seb - Julien.
 */
@Service
public class BatchPGM implements Runnable {

    @Autowired
    CompetenceRepository mCompetenceRepository;

    private CompetencesTreeRadioVisitor competenceTreeVisitor;
    private CompetencesTreeWalker competenceTreeWalker;

    public static void main(String[] args) {

        BatchUtil.startSpringBatch(BatchPGM.class);

    }

    @Override
    public void run() {

        Competence cOne = mCompetenceRepository.find(1L);
        
        if(cOne != null){
            
        competenceTreeVisitor = new CompetencesTreeRadioVisitor(cOne);
        competenceTreeWalker = new CompetencesTreeWalker(mCompetenceRepository,
                competenceTreeVisitor, cOne);

        competenceTreeWalker.walk();

        System.out.println(competenceTreeVisitor.getHtmlResult());
        
        }else{
            
            System.out.println("wait for updating repository competence .. .. ");
        }

    }

}
