package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Rating;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class RatingRepository extends BaseRepository<Rating>{
	
	public Object[]  avgAndCountRating(Resource resource){
		return (Object[])em.createQuery("select avg(r.score), count(r) from  Rating r where r.resource=:resource")
				.setParameter("resource", resource)
				.getSingleResult();
		
	}
	
	public List<Rating> listRating(List<Resource> listResource, User user) {
		List<Rating> listRating = (List<Rating>) em.createQuery("SELECT ra.score FROM Rating ra WHERE ra.user=:user AND ra.resource in (:listResource)")
				.setParameter("user", user)
				.setParameter("listResource", listResource)
				.getResultList();
		
		return listRating;
	}
	
	public Rating getRatingForUserAndResource(Resource resource, User user){
	    return (Rating)getSingleOrNullResult( em.createQuery("select r from  Rating r where r.resource_id=:resource and r.user_id=:user")
		.setParameter("resource",resource)
		.setParameter("user",user) );
	}
   
	
}
