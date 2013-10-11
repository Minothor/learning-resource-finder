package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Problem;
import learningresourcefinder.model.Resource.Topic;
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
	
	public Long countOpenProblemsForTopic(Topic topic) {
		Long result = (long) em.createQuery("SELECT COUNT(p) FROM Problem p where p.resolved = false and p.resource.topic = :topic")
				.setParameter("topic", topic)
				.getSingleResult();
		return result;
	}
	
	public Long countProblemOfFieldNull(Topic topic) {
		Long result = (long) em.createQuery("SELECT COUNT(r) FROM Resource r where (r.name = null OR r.description = null OR r.language = null OR r.format = null"
				+ " OR r.platform = null OR r.nature = null OR r.numberImage = null) AND r.topic = :topic")
				.setParameter("topic", topic)
				.getSingleResult();
		return result;
	}
	
	public Long countProblemOfCompetenceNull(Topic topic) {
		Long result = (long) em.createQuery("SELECT COUNT(r) FROM Resource r  WHERE 0 = SIZE(r.competences) AND r.topic = :topic")
				.setParameter("topic", topic)
				.getSingleResult();
		return result;
	}
	
	
	
 
	
}


