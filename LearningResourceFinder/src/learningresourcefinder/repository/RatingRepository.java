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
	    
	    if(listResource.size()==0){listResource = null;} //fix bug when listResource is instanced with a size of 0 but not null, causing sql query below error
	    
		List<Rating> listRating = (List<Rating>) em.createQuery("SELECT ra FROM Rating ra WHERE ra.user=:user AND ra.resource in (:listResource)")
				.setParameter("user", user)
				.setParameter("listResource", listResource)
				.getResultList();
		
		return listRating;
	}
	
	public Rating getRatingForUserAndResource(Resource resource, User user){
	    return (Rating)getSingleOrNullResult( em.createQuery("select r from  Rating r where r.resource=:resource and r.user=:user")
		.setParameter("resource",resource)
		.setParameter("user",user) );
	}
   
	
}
