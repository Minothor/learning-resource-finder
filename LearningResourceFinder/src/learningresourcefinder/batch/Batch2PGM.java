package learningresourcefinder.batch;



import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.service.CompetenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class Batch2PGM implements Runnable {

    @Autowired CompetenceService competenceService;
    @Autowired CycleRepository repCycle;
    
   // 
    
    
    public static void main(String[] args) {
        BatchUtil.startSpringBatch(Batch2PGM.class);
    }

    @Override
    public void run() {

    }
}
