package learningresourcefinder.batch;

import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.service.CompetenceNodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CompetenceNodeBatch implements Runnable{

	@Autowired CompetenceNodeService competenceNodeService;
	@Autowired CycleRepository cycleRepository;

   public static void main(String[] args){
       BatchUtil.startSpringBatch(CompetenceNodeBatch.class);
   }

   @Override
    public void run() {
        Cycle cycle= cycleRepository.find(33L);
        
	   
        if(cycle!=null){
        	competenceNodeService.buildCompetenceNodeTree(cycle);
        }
	   
    }
}
