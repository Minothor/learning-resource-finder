package learningresourcefinder.repository;

import learningresourcefinder.model.Rating;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class RatingRepository extends BaseRepository<Rating>{
	
	public Double  avgRating(Resource resource){
		return (Double)em.createQuery("select avg(r.score) from  Rating r where r.resource=:resource")
				.setParameter("resource", resource)
				.getSingleResult();
		
	}
	
	public Rating getRatingForUserAndResource(Resource resource, User user){
	    return (Rating)getSingleOrNullResult( em.createQuery("select r from  Rating r where r.resource_id=:resource and r.user_id=:user")
		.setParameter("resource",resource)
		.setParameter("user",user) );
	}
   
	
}
