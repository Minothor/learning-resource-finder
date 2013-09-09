package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Resource;

import org.springframework.stereotype.Repository;



@Repository
@SuppressWarnings("unchecked")
public class CompetenceRepository extends BaseRepository<Competence> {

	public List<Resource> findResourceByProgramPointAndSubs(Competence startProgramPoint) {
		// 1. We recursively collect all the sub-program points
		List<Competence> programPoints = startProgramPoint.getChildrenAndSubChildren();
		programPoints.add(startProgramPoint);
		
		// 2. We query
		List<Resource> result = em
				.createQuery("SELECT distinct r FROM Resource r join r.programPoints pp WHERE pp in (:programPoints)")
				.setParameter("programPoints", programPoints)
				.getResultList();

		return result;
	}
	
	
    public Competence findByCode(String code){
        return getSingleOrNullResult(
        		em.createQuery("select pp from ProgramPoint pp where lower(pp.code) = :code")
        		.setParameter("code",code.toLowerCase())
        );
    }
    public List<Competence> findAllWithoutParent(){
        return    em.createQuery("select a from Competence a where a.parent is null ").getResultList();
    }
    
}
