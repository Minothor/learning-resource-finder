package learningresourcefinder.batch;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CompetenceNodeBatch implements Runnable{


   public static void main(String[] args){
       BatchUtil.startSpringBatch(CompetenceNodeBatch.class);
   }

   @Override
    public void run() {
        // TODO Auto-generated method stub
    }
}
