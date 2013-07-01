package learningresourcefinder.batch;

import java.util.Date;

import learningresourcefinder.model.Comment;
import learningresourcefinder.model.Problem;
import learningresourcefinder.model.ProgramPoint;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.School;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.AccountStatus;
import learningresourcefinder.repository.CommentRepository;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.repository.ProgramPointRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.SchoolRepository;
import learningresourcefinder.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class InitializeDBBatch implements Runnable {
	
	@Autowired
	ProblemRepository problemRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	ResourceRepository resourceRepository;
	
	@Autowired
	SchoolRepository schoolRepository;
	
	@Autowired
	ProgramPointRepository programPointRepository;

	
	Resource frDupont;
	Resource mathGob;
	
	public static void main(String[] args) {
		BatchUtil.startSpringBatch(InitializeDBBatch.class);
	}

	@Override
	public void run() {
		insertSchool();
		insertUser();
		insertResource();
		insertProblem();
		insertProgramPoints();
		System.out.println("DataBase Initialized !");
	}
	
	public void insertSchool() {
		School sc = new School();
		sc.setAddress("Rue du village 157, 5352 Perwez");
		sc.setName("Ecole communale de perwez");
		schoolRepository.persist(sc);
		System.out.println("School Done !");
	}
	
	public void insertUser() {	
		School school = schoolRepository.find(1L);		
		User u = new User();
		u.setFirstName("titi");
		u.setLastName("tutu");
		u.setBirthDate(new Date());
		u.setMail("toto@tata.com");
		u.setValidationCode("2fd5f4d5f4d5f4d5f4");
		u.setAccountStatus(AccountStatus.ACTIVE);
		u.setConsecutiveFailedLogins(0);
		u.setPasswordKnownByTheUser(true);
		u.setNlSubscriber(false);	
		u.setPicture(false);
		u.setSpammer(false);
		u.setUserName("tato");
		u.addSchool(school);
		userRepository.persist(u);
		System.out.println("User Done !");
	}

	public void insertProblem() {
		User u = userRepository.find(1L);
		Resource r = resourceRepository.getResourceByName("Français");
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
		System.out.println("Problem & Comment Done !");
	}
	
	public void insertResource() {
		frDupont = new Resource("Français - Dupont", "cours de français pour débutant écrit par Charlotte Dupont");
		mathGob = new Resource("Goblin-Math", "Jeu de mathématiques avec des goblins.");
		frDupont.setUser(userRepository.getUserByUserName("tato"));
		mathGob.setUser(userRepository.getUserByUserName("toto"));
		resourceRepository.persist(frDupont);
		resourceRepository.persist(mathGob);
		System.out.println("Resource Done !");
	}
	
	public void insertProgramPoints() {
		ProgramPoint pFond = new ProgramPoint("Fon", "Fondamental");
		programPointRepository.persist(pFond);

		ProgramPoint p1 = new ProgramPoint("1P", "1ère primaire");
		programPointRepository.persist(p1);
		
		ProgramPoint p2 = new ProgramPoint("2P", "2e primaire");
		programPointRepository.persist(p2);
		
		ProgramPoint p1M = new ProgramPoint("2PM", "Math");
		programPointRepository.persist(p1M);

		ProgramPoint p1M1 = new ProgramPoint("2PM.Num", "Numération");
		programPointRepository.persist(p1M1);

		ProgramPoint p1M2 = new ProgramPoint("2PM.Add", "Additions");
		programPointRepository.persist(p1M2);
		
		ProgramPoint p1F = new ProgramPoint("2PF", "Français");
		programPointRepository.persist(p1F);

		pFond.addChild(p1);
		p1.addChild(p1M);
		p1M.addChild(p1M1);
		p1M.addChild(p1M2);
		p1.addChild(p1F);
		
		p1F.addResource(frDupont);
		
		p1M1.addResource(mathGob);
		
		System.out.println("Program points done!");
	}


}
