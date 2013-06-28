package learningresourcefinder.repository;

import java.util.ArrayList;
import java.util.List;

import learningresourcefinder.model.ProgramPoint;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ProgramPointRepository extends BaseRepository<ProgramPoint> {

	public List<ProgramPoint> findResourceByProgramPointAndSubs(ProgramPoint startProgramPoint) {
		// 1. We recursively collect all the sub-program points
		List<ProgramPoint> programPoints = startProgramPoint.getChildrenAndSubChildren();
		
		// 2. We query
		List<ProgramPoint> result = em
				.createQuery("SELECT r FROM Resource r  WHERE r.programPoint in (:programPoints)")
				.setParameter("programPoints", programPoints)
				.getResultList();

		return result;
	}
}
