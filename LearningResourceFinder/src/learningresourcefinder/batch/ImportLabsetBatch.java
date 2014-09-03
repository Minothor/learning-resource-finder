package learningresourcefinder.batch;


import learningresourcefinder.service.ImportLabSetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class ImportLabsetBatch implements Runnable {

    @Autowired ImportLabSetService importLabSetService;
   
    public static void main(String[] args)  {
        BatchUtil.startSpringBatch(ImportLabsetBatch.class);
    }

    public void run() {
    	importLabSetService.importFrancais();
    	importLabSetService.importMaths();
    }
}




