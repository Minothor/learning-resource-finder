package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.UrlGeneric;

import org.springframework.stereotype.Repository;

@Repository
public class UrlGenericRepository extends BaseRepository<UrlGeneric> {

    @SuppressWarnings("unchecked")
    public List<UrlGeneric> findAllUrlGeneric() {
        return (List<UrlGeneric>) em.createQuery("select u from  UrlGeneric u")
                .getResultList();
    }

    public UrlGeneric findByurl(String url) {
        return getSingleOrNullResult(em.createQuery("select u from UrlGeneric u where u.url =:url").setParameter("url", url));
    }

}
