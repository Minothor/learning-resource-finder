package learningresourcefinder.batch;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import learningresourcefinder.model.Problem;
import learningresourcefinder.model.ProgramPoint;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Task;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.ProgramPointRepository;
import learningresourcefinder.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QueryTestBatch implements Runnable {

	@Autowired ProgramPointRepository programPointRepository;
	@Autowired ResourceRepository resourceRepository;
	@PersistenceContext EntityManager em;
	
	public static void main(String[] args) {
		BatchUtil.startSpringBatch(QueryTestBatch.class);
	}
	
	@Override
	public void run() {
		ProgramPoint pFond = programPointRepository.findByCode("Fon");
		List<Resource> resList = programPointRepository.findResourceByProgramPointAndSubs(pFond);
		System.out.println(resList);		
		
		//User u = userRepository.getUserByUserName("deli");
		//List<Task> taskList = taskRepository.findTasksOfAssigner(u);
		//System.out.println("La liste des tâches : " + taskList.toString());
		
		//List<Problem> problemList = problemRepository.findProblemOfAuthor(u);
		//System.out.println("La liste des problèmes : " + problemList.toString());
		
		//List<Resource> resourceList = resourceRepository.findAllResourceByUser(u);
		//System.out.println("Les ressources d'un auteur : " + resourceList);
		
//		ProgramPoint p2 = programPointRepository.findByCode("2p");
//		Resource mathGob = resourceRepository.getResourceByName("Goblin-Math");
//		p2.addResource(mathGob);
//		em.flush();
		
		ProgramPoint pFond = programPointRepository.findByCode("Fon");
		List<Resource> resList = programPointRepository.findResourceByProgramPointAndSubs(pFond);
		System.out.println(resList);
	}
}
