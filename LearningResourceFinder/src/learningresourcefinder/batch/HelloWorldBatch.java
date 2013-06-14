package learningresourcefinder.batch;

import learningresourcefinder.model.User;
import learningresourcefinder.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldBatch implements Runnable {
	
	@Autowired UserRepository userRepository;
	
	public static void main(String[] args) {
		BatchUtil.startSpringBatch(HelloWorldBatch.class);
	}

	@Override
	public void run() {
		System.out.println("Hello World");
		
		User u = userRepository.find(1L);
		System.out.println(u.getFirstName());
		
		
	}
}
