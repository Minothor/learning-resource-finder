package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Task;
import learningresourcefinder.model.User;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings ("unchecked")
public class TaskRepository extends BaseRepository<Task>
{
	public List<Task> findTaskOfStudent(User student) {
		List<Task> results = em.createQuery("SELECT t FROM Task t WHERE t.student = :student")
				.setParameter("student", student)
				.getResultList();		
		return results;
	}
	
	public List<Task> findTaskOfAssigner(User assigner) {
		List<Task> results = em.createQuery("SELECT t FROM Task t WHERE t.assigner = :assigner")
				.setParameter("assigner", assigner)
				.getResultList();		
		return results;
	}
}