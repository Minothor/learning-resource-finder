package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Problem;
import learningresourcefinder.model.User;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ProblemRepository extends BaseRepository<Problem>
{

	public Problem sortProblemByDateAsc() {
		List<Problem> results = em.createQuery("SELECT p FROM Problem p ORDER BY p.date ASC").getResultList();
		return (Problem) results;
	}
	
	public Problem sortProblemByDateDesc() {
		List<Problem> results = em.createQuery("SELECT p FROM Problem p ORDER BY p.date DESC").getResultList();
		return (Problem) results;
	}
	
	public List<Problem> findProblemOfAuthor(User createdBy) {
		List<Problem> results = em.createQuery("SELECT p FROM Problem p WHERE p.createdBy = :createdBy ORDER BY p.id DESC")
				.setParameter("createdBy", createdBy)
				.getResultList();
		return results;
	}
}


