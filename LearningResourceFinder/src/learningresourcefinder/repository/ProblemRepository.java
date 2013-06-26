package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Problem;
import learningresourcefinder.model.User;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ProblemRepository extends BaseRepository<Problem>
{
	public User findUserByProblem(Problem problem) {
		return (User) em.createQuery("SELECT u FROM User u WHERE u.id = :user").setParameter("user", problem.getUser().getId());
	}
	
	public Problem sortProblemByDateAsc() {
		List<Problem> results = em.createQuery("SELECT p FROM Problem p ORDER BY p.date ASC").getResultList();
		return (Problem) results;
	}
	
	public Problem sortProblemByDateDesc() {
		List<Problem> results = em.createQuery("SELECT p FROM Problem p ORDER BY p.date DESC").getResultList();
		return (Problem) results;
	}
}
