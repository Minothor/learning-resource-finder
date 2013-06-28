package learningresourcefinder.batch;

import learningresourcefinder.model.Comment;
import learningresourcefinder.model.Problem;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.CommentRepository;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemBatch implements Runnable
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
		BatchUtil.startSpringBatch(ProblemBatch.class);
	}

	@Override
	public void run() {
		insertProblem();
		System.out.println("Done");
	}
	
	public void insertProblem() {
		
		User u = userRepository.find(1L);
		Resource r = resourceRepository.getResourceByTitle("Français");
		
		Problem p = new Problem();
		Comment c = new Comment();
		
		c.setDescription("Commentaire sur le problème numéro 1 :D");
		c.setTitle("Problème 1");
		c.setProblem(p);
		c.setAuthor(u);
		
		p.addComment(c);
		p.setResolved(false);
		p.setResource(r);
		p.setAuthor(u);
		p.setDescription("Problème numéro 1");
		p.setTitle("Mon problème ^^");
		
		problemRepository.persist(p);
		commentRepository.persist(c);
	}
}