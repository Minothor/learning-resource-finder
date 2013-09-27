package learningresourcefinder.controller;

import learningresourcefinder.model.Rating;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.RatingRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class RatingController extends BaseController<Rating> {

    @Autowired RatingRepository ratingRepository;
    @Autowired ResourceRepository resourceRepository;
    @Autowired UserRepository  userRepository;
    
    @RequestMapping("ajax/rateresource")  // ajax
    public @ResponseBody String rateResource(@RequestParam("idresource")long idResource,@RequestParam("score") Double score) {
    	SecurityContext.assertUserIsLoggedIn();
        Resource resource = (Resource) getRequiredEntity(idResource,Resource.class);
        Rating rating = ratingRepository.getRatingForUserAndResource(resource,SecurityContext.getUser());
        if (rating == null) {
        	rating = new Rating(score, resource, SecurityContext.getUser());
        	ratingRepository.persist(rating);
        } else {
        	rating.setScore(score);
			ratingRepository.merge(rating);
		}
		resource.setScore(ratingRepository.avgRating(resource));
		return "success";
	}
}
