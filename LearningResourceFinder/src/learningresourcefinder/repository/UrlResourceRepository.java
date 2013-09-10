package learningresourcefinder.repository;


import java.util.List;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class UrlResourceRepository extends BaseRepository<UrlResource> {
	
	public Resource getFirstResourceWithSimilarUrl(String url) {
		List<Resource> result = (List<Resource>) em.createQuery("SELECT u.resource FROM UrlResource u  WHERE u.url = :url").setParameter("url", url).getResultList();
		
        return (result == null || result.size() == 0 ? null : result.get(0));
	}
	
}
