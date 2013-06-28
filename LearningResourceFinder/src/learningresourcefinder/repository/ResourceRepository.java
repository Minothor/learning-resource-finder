package learningresourcefinder.repository;

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
	
	public User findAuthorOfResource(Resource resource) {
		return (User) em.createQuery("SELECT u FROM Resource r JOIN r.user u WHERE r.user = :user")
				.setParameter("user", resource.getUser()).getSingleResult();
	}
}