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
       System.out.println("Resource  : " +ratingRepository.avgRating(resource));
       resource.setScore(ratingRepository.avgRating(resource));
       
       Resource resource1 = resourceRepository.find(1L);
       System.out.println("Resource 1 : " +ratingRepository.avgRating(resource1));
       resource1.setScore(ratingRepository.avgRating(resource1));
    }
}
