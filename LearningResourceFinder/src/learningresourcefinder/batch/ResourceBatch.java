package learningresourcefinder.batch;

import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceBatch implements Runnable {

	@Autowired
	ResourceRepository resourceRepository;

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		BatchUtil.startSpringBatch(ResourceBatch.class);
	}

	public void insertResource() {
		Resource r = new Resource();
		r.setDescription("cours de français pour débutant");
		r.setTitle("Français");
		r.setUser(userRepository.getUserByUserName("tato"));
		resourceRepository.persist(r);
	}

	@Override
	public void run() {
		insertResource();
	}
}
