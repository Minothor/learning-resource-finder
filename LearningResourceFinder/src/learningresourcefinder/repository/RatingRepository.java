package learningresourcefinder.repository;

import learningresourcefinder.model.Rating;
import learningresourcefinder.model.Resource;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class RatingRepository extends BaseRepository<Rating>{
	
	public Double  avgRating(Resource resource){
		return (Double)em.createQuery("select avg(r.score) from  Rating r where r.resource=:resource")
				.setParameter("resource", resource)
				.getSingleResult();
		
	}

}
