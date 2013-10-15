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
}
