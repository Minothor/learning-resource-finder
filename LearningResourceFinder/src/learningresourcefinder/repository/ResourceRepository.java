package learningresourcefinder.repository;

import java.util.List;

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
	
	public List<Resource> findAllResourceByUser(User author) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE r.author = :author")
				.setParameter("author", author)
				.getResultList();
		
		return results;
	}
}