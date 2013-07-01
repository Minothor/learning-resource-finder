package learningresourcefinder.batch;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import learningresourcefinder.model.ProgramPoint;
import learningresourcefinder.model.Resource;
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
//		ProgramPoint p2 = programPointRepository.findByCode("2p");
//		Resource mathGob = resourceRepository.getResourceByName("Goblin-Math");
//		p2.addResource(mathGob);
//		em.flush();
		
		ProgramPoint pFond = programPointRepository.findByCode("Fon");
		List<Resource> resList = programPointRepository.findResourceByProgramPointAndSubs(pFond);
		System.out.println(resList);
	}
}
