package learningresourcefinder.batch;

import java.util.List;

import learningresourcefinder.model.ProgramPoint;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ProgramPointRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QueryTestBatch implements Runnable {

	@Autowired ProgramPointRepository programPointRepository;
	
	public static void main(String[] args) {
		BatchUtil.startSpringBatch(QueryTestBatch.class);
	}
	
	@Override
	public void run() {
		ProgramPoint pFond = programPointRepository.findByCode("Fon");
		List<Resource> resList = programPointRepository.findResourceByProgramPointAndSubs(pFond);
		System.out.println(resList);
	}
}
