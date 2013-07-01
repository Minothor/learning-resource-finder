package learningresourcefinder.repository;

import org.springframework.stereotype.Repository;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;

@Repository
@SuppressWarnings("unchecked")
public class ResourceRepository extends BaseRepository<Resource>
{
	public Resource getResourceByName(String name) {
		return getSingleOrNullResult(em.createQuery("SELECT r FROM Resource r WHERE r.name =:name")
                .setParameter("name", name));
	}
	
	public User findAuthorOfResource(Resource resource) {
		return (User) em.createQuery("SELECT u FROM Resource r JOIN r.user u WHERE r.user = :user")
				.setParameter("user", resource.getUser()).getSingleResult();
	}
}