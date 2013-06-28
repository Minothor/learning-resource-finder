package learningresourcefinder.batch;

import learningresourcefinder.model.School;
import learningresourcefinder.repository.SchoolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchSchool implements Runnable
{
	@Autowired
	SchoolRepository schoolRepository;
	
	public static void main(String[] args) {
		BatchUtil.startSpringBatch(BatchSchool.class);
	}

	@Override
	public void run() {
		insertSchool();
		System.out.println("Done");
	}
	
	public void insertSchool() {
		School sc = new School();
		sc.setAddress("Rue du village 157, 5352 Perwez");
		sc.setCountry("Belgique");
		sc.setName("Ecole communale de perwez");
		schoolRepository.persist(sc);
	}

}
