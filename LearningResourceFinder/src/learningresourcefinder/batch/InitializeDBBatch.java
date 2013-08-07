package learningresourcefinder.batch;

import java.util.Date;

import learningresourcefinder.model.Comment;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Problem;
import learningresourcefinder.model.ProgramPoint;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.School;
import learningresourcefinder.model.Task;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.AccountStatus;
import learningresourcefinder.repository.CommentRepository;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.repository.ProgramPointRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.SchoolRepository;
import learningresourcefinder.repository.TaskRepository;
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
	
	@Autowired
	TaskRepository taskRepository;

	
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
		insertTask();
		System.out.println("DataBase Initialized !");
	}
	
	public void insertSchool() {
		School sc = new School();
		sc.setAddress("Rue du village 157, 5352 Perwez");
		sc.setName("Ecole1");
		schoolRepository.persist(sc);
		System.out.println("School Done !");
	}
	
	public void insertUser() {	
		School school = schoolRepository.find(9L);		
		User u = new User();
		u.setFirstName("Thomas");
		u.setLastName("Delizee");
		u.setBirthDate(new Date());
		u.setMail("thomasdelizee@gmail.com");
		u.setValidationCode("2fd5f4d5f4d5f4d5f4");
		u.setAccountStatus(AccountStatus.ACTIVE);
		u.setConsecutiveFailedLogins(0);
		u.setPasswordKnownByTheUser(true);
		u.setPassword("code");
		u.setNlSubscriber(false);	
		u.setPicture(false);
		u.setSpammer(false);
		u.setUserName("deli");
		u.getSchools().add(school);
		userRepository.persist(u);
		System.out.println("User Done !");
	}

	public void insertProblem() {
		User u = userRepository.getUserByUserName("deli");
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
		p.setName("Mon problème ^^");
		problemRepository.persist(p);
		commentRepository.persist(c);
		System.out.println("Problem & Comment Done !");
	}
	
	public void insertResource() {
		frDupont = new Resource("Français - Dupont", "cours de français pour débutant écrit par Charlotte Dupont");
		mathGob = new Resource("Goblin-Math", "Jeu de mathématiques avec des goblins.");
		frDupont.setUser(userRepository.getUserByUserName("deli"));
		mathGob.setUser(userRepository.getUserByUserName("deli"));
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
		
		ProgramPoint p1M = new ProgramPoint("1PM", "Math");
		programPointRepository.persist(p1M);

		ProgramPoint p1M1 = new ProgramPoint("1PM.Num", "Numération");
		programPointRepository.persist(p1M1);

		ProgramPoint p1M2 = new ProgramPoint("1PM.Add", "Additions");
		programPointRepository.persist(p1M2);
		
		ProgramPoint p1F = new ProgramPoint("1PF", "Français");
		programPointRepository.persist(p1F);

		pFond.addChild(p1);
		pFond.addChild(p2);
		p1.addChild(p1M);
		p1M.addChild(p1M1);
		p1M.addChild(p1M2);
		p1.addChild(p1F);
		
		p1F.addResource(frDupont);
		
		p1M1.addResource(mathGob);
		
		System.out.println("Program points done!");
	}
	
	public void insertTask() {
		Task t = new Task();
		User u = userRepository.getUserByUserName("deli");
		t.setAssigner(u);
		t.setName("Tâche 1");
		t.setUser(u);
		taskRepository.persist(t);
		System.out.println("Task Done !");
	}
	
	public void insetPlayList() {
	    PlayList p = new PlayList();
		p.setName("PlayList 1");
		
	}
}
