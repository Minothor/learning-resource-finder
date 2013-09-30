package learningresourcefinder.batch;



import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.RatingRepository;
import learningresourcefinder.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RatingTestBatch implements Runnable {

    @Autowired RatingRepository ratingRepository;
    @Autowired ResourceRepository resourceRepository;
    
    public static void main(String[] args) {
        BatchUtil.startSpringBatch(RatingTestBatch.class);
    }

    @Override
    public void run() {
       Resource resource = resourceRepository.find(2L);
       Object[] avgAndCount = ratingRepository.avgAndCountRating(resource);
        resource.setAvgRatingScore((Double)avgAndCount[0]);
        
       resource.setCountRating((Long)avgAndCount[1]);
       System.out.println("Score avg  : "+resource.getAvgRatingScore());
       System.out.println("Counter    : "+resource.getCountRating());

    	   
      
       
      
       
       Resource resource1 = resourceRepository.find(1L);
       Object[] avgAndCount1 = ratingRepository.avgAndCountRating(resource);
       
    }
}
