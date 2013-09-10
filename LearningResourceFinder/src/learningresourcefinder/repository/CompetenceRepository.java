package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Resource;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class CompetenceRepository extends BaseRepository<Competence> {

	public List<Resource> findResourceByCompetenceAndSubs(Competence startCompetence) {
		// 1. We recursively collect all the sub-program points
		List<Competence> competences = startCompetence.getChildrenAndSubChildren();
		competences.add(startCompetence);
		
		// 2. We query
		List<Resource> result = em
				.createQuery("SELECT distinct r FROM Resource r join r.competences c WHERE c in (:competences)")
				.setParameter("competences", competences)
				.getResultList();

		return result;
	}
	
	
    public Competence findByCode(String code){
        return getSingleOrNullResult(
        		em.createQuery("select c from Competence c where lower(c.code) = :code")
        		.setParameter("code",code.toLowerCase())
        );
    }
    public List<Competence> findAllWithoutParent(){
        return    em.createQuery("select c from Competence c where c.parent is null ").getResultList();
    }
    
}
