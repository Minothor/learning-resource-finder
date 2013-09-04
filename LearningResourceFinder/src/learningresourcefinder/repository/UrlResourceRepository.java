package learningresourcefinder.repository;


import learningresourcefinder.model.UrlResource;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class UrlResourceRepository extends BaseRepository<UrlResource> {
	
	public UrlResource getResourceByUrl(String url) {
		return getSingleOrNullResult(em.createQuery("SELECT u FROM UrlResource r WHERE u.url =:url")
                .setParameter("url", url));
	}
	
}
