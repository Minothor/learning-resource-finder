package learningresourcefinder.repository;


import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.User;

import org.springframework.stereotype.Repository;


@Repository
@SuppressWarnings ("unchecked")
public class PlayListRepository extends BaseRepository<PlayList> {

	public PlayList findByNameAndAuthor(String name, User author) {
        return getSingleOrNullResult(   
        		em.createQuery("select p from PlayList p where lower(p.name)=:name and p.createdBy = :author")
            	.setParameter("name", name.toLowerCase())
            	.setParameter("author", author)
        );
    }
	
	
	public PlayList findByDescription(String description) {
        return getSingleOrNullResult(   
        		em.createQuery("select d from Description d where lower(d.description)=:description")
            	.setParameter("description", description.toLowerCase())
        );
    }
}
