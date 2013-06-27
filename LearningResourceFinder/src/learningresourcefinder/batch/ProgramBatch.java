package learningresourcefinder.batch;

import learningresourcefinder.model.Program;
import learningresourcefinder.repository.ProgramRepository;
import learningresourcefinder.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramBatch implements Runnable {
	
	Program p = new Program();
	
	@Autowired
	ProgramRepository programRepository;
	
	@Autowired
	ResourceRepository resourceRepositiry;

	public static void main(String[] args) {
		BatchUtil.startSpringBatch(ProgramBatch.class);
	}
	
	public void insertProgram () {
		
		p.setDescription("première primaire");
		p.setDuration(180);
		p.setLevel(2);
		p.setName("Première primaire ( 1P )");
		p.addResource(resourceRepositiry.getResourceByTitle("Français"));
		programRepository.persist(p);
	}
	
	@Override
	public void run() {
		insertProgram();
		
	}
}
