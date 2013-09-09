package learningresourcefinder.batch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Service;

/*
 *  BatchPGM Class For PGM/Matiere Test. 
 *  Author Ahmed Idoumhaidi.
 *  Group : Seb - Julien.
 */
@Service
public class BatchPGM implements Runnable {

    
   
    public static void main(String[] args) {

        BatchUtil.startSpringBatch(BatchPGM.class);

    }

    @Override
    public void run() {
        // get the root competency (through repository)   (parent = null)
        
        // new visitor
        // new Walker
        // run walker
    }
    
    


    
}
