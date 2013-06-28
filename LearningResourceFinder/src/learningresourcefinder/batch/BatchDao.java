package learningresourcefinder.batch;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.CommentRepository;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchDao implements Runnable
{
	@Autowired
	ProblemRepository problemRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	ResourceRepository resourceRepository;

	public static void main(String[] args) {
		BatchUtil.startSpringBatch(BatchDao.class);
	}

	@Override
	public void run() {
		User u = userRepository.find(1L);
		System.out.println(u.getMail());
		
		Resource r = resourceRepository.getResourceByTitle("Français");
		System.out.println(resourceRepository.findUserOfResource(r).getFirstName());
	}

}
