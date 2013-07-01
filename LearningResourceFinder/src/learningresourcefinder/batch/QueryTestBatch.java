package learningresourcefinder.batch;

import learningresourcefinder.model.ProgramPoint;
import learningresourcefinder.repository.ProgramPointRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryTestBatch implements Runnable {

	@Autowired ProgramPointRepository programPointRepository;
	
	public static void main(String[] args) {
		BatchUtil.startSpringBatch(QueryTestBatch.class);
	}
	
	@Override
	public void run() {
		ProgramPoint pFond = programPointRepository.find....
		programPointRepository.find....(pFond);
	}
}
