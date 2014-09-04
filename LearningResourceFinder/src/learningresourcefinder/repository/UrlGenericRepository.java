package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.UrlGeneric;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class UrlGenericRepository extends BaseRepository<UrlGeneric> {

    public List<UrlGeneric> findAll() {

        return (List<UrlGeneric>) em.createQuery("select u from  UrlGeneric u")
                .getResultList();

    }

}
