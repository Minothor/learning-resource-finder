package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Favorite;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class FavoriteRepository extends BaseRepository<Favorite> {
	public List<Resource> findAllResourceFavoriteForUser(User user) {
		List<Resource> results = em.createQuery("SELECT f.resource FROM Favorite f WHERE f.user = :user ORDER BY f.createdOn")
				.setParameter("user", user)
				.getResultList();
		return results;
	}
	
	public Favorite findFavorite(Resource resource, User user) {
		Favorite favorite = (Favorite) em.createQuery("SELECT f FROM Favorite f WHERE f.resource = :resource AND f.user = :user")
				.setParameter("resource", resource)
				.setParameter("user", user)
				.getSingleResult();
		return favorite;
	}
	
	public boolean isFavorite(Resource resource, User user) {
		long result = (long) em.createQuery("SELECT COUNT(f) FROM Favorite f WHERE f.resource = :resource AND f.user = :user") 
				.setParameter("resource", resource)
				.setParameter("user", user)
				.getSingleResult();
		
		if(result > 0)
			return true;
		else
			return false;
	}
}
