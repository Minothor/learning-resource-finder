package learningresourcefinder.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;

@Repository
@SuppressWarnings("unchecked")
public class ResourceRepository extends BaseRepository<Resource>
{
	public Resource getResourceByTitle(String title) {
		return getSingleOrNullResult(em.createQuery("SELECT r FROM Resource r WHERE r.title =:title")
                .setParameter("title", title));
	}
	
	public List<Resource> findResourceByLikeTitle(String title) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE r.title LIKE :title")
				.setParameter("title", "%"+ title +"%").getResultList();
		return results;
	}
	
	public List<Resource> findResourceByLikeDescription(String description) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE r.description LIKE :description")
				.setParameter("description", "%" + description + "%").getResultList();
		return results;
	}
	
	public Long findTotalNumberResource() {
		return (Long)em.createQuery("SELECT COUNT(r.id) FROM Resource r").getSingleResult();
	}
	
	public User findUserOfResource(Resource resource) {
		return (User) em.createQuery("SELECT u FROM User u JOIN Resource r WHERE r.user = :user")
				.setParameter("user", resource.getUser()).getSingleResult();
	}
}