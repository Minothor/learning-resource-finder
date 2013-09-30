package learningresourcefinder.controller;

import learningresourcefinder.model.Rating;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.RatingRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class RatingController extends BaseController<Rating> {

    @Autowired RatingRepository ratingRepository;
    @Autowired ResourceRepository resourceRepository;
    @Autowired UserRepository  userRepository;
    
    @RequestMapping("ajax/rateresource")  // ajax
    public @ResponseBody String rateResource(@RequestParam("idresource")long idResource,@RequestParam("score") int score) {
    	score++; // On the client side, 1 star = 0 (because component used) and server side 1 star = 1 (to get correct numbers in averages and co).
    	SecurityContext.assertUserIsLoggedIn();
        Resource resource = resourceRepository.find(idResource);
        
        Rating rating = ratingRepository.getRatingForUserAndResource(resource,SecurityContext.getUser());
        if (rating == null) {
        	rating = new Rating((double)score, resource, SecurityContext.getUser());
        	ratingRepository.persist(rating);
        } else {
        	rating.setScore((double)score);
			ratingRepository.merge(rating);
		}
        Object[] avgAndCount = ratingRepository.avgAndCountRating(resource);
		resource.setAvgRatingScore((Double)avgAndCount[0]);
		resource.setCountRating((Long)avgAndCount[1]);
		
		return "success";
	}
}
