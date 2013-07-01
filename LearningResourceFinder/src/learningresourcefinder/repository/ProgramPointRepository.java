package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.ProgramPoint;
import learningresourcefinder.model.Resource;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ProgramPointRepository extends BaseRepository<ProgramPoint> {

	public List<Resource> findResourceByProgramPointAndSubs(ProgramPoint startProgramPoint) {
		// 1. We recursively collect all the sub-program points
		List<ProgramPoint> programPoints = startProgramPoint.getChildrenAndSubChildren();
		programPoints.add(startProgramPoint);
		
		// 2. We query
		List<Resource> result = em
				.createQuery("SELECT r FROM Resource r join r.programPoints pp WHERE pp in (:programPoints)")
				.setParameter("programPoints", programPoints)
				.getResultList();

		return result;
	}
	
	
    public ProgramPoint findByCode(String code){
        return getSingleOrNullResult(
        		em.createQuery("select pp from ProgramPoint pp where lower(pp.code) = :code")
        		.setParameter("code",code.toLowerCase())
        );
    }
    
}
