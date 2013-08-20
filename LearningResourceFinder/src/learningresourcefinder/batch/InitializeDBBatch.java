package learningresourcefinder.batch;

import java.util.Date;

import learningresourcefinder.model.Comment;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Problem;
import learningresourcefinder.model.ProgramPoint;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.School;
import learningresourcefinder.model.Task;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.AccountStatus;
import learningresourcefinder.model.User.Role;
import learningresourcefinder.repository.CommentRepository;
import learningresourcefinder.repository.PlayListRepository;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.repository.ProgramPointRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.SchoolRepository;
import learningresourcefinder.repository.TaskRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.util.SecurityUtils;

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

	@Autowired
	PlayListRepository playListRepository;
	
	@Autowired
	UrlResourceRepository urlResourceRepository;
	
	Resource frDupont;
	Resource mathGob;
    Resource frOrtho1;
    Resource mathFraction1;	

	School school;  // Various methods need the created school
	
	public static void main(String[] args) {
		BatchUtil.startSpringBatch(InitializeDBBatch.class);
	}
	
	// UPDATE resource set createdby_id = 8 WHERE name = 'Français - Dupont' AND name = 'Goblin-Math';

	@Override
	public void run() {
		insertSchool();
		insertUser();
		insertResource();
		insertProblem();
		insertProgramPoints();
		insertTask();
		insertPlayList();
		insertUrlResource();
		System.out.println("DataBase Initialized !");
	}
	
	public void insertSchool() {
	    school = new School();
	    school.setAddress("Rue du village 157, 5352 Perwez");
	    school.setName("Ecole1");
		schoolRepository.persist(school);
		System.out.println("School Done !");
	}
	
	public void insertUser() {
		// Admin user
		User u = new User();
		u.setFirstName("Admin");
		u.setLastName("");
		u.setBirthDate(new Date());
		u.setMail("info@lrf.be");
		u.setAccountStatus(AccountStatus.ACTIVE);
		u.setUserName("admin");
	    u.setRole(Role.ADMIN);
	    u.hasAdminPrivileges();
	    u.setPassword(SecurityUtils.md5Encode("aaaa"));
		userRepository.persist(u);

	    
	        
	     // Normal user
	        User nUser = new User();
	        nUser.setFirstName("Thomas");
	        nUser.setLastName("Delizee");
	        nUser.setBirthDate(new Date());
	        nUser.setMail("thomasdelizee@gmail.com");
	        nUser.setValidationCode("2fd5f4d5f4d5f4d5f4");
	        nUser.setAccountStatus(AccountStatus.ACTIVE);
	        nUser.setConsecutiveFailedLogins(0);
	        nUser.setPasswordKnownByTheUser(true);
	        nUser.setNlSubscriber(false);   
	        nUser.setPicture(false);
	        nUser.setSpammer(false);
	        nUser.setUserName("deli");
	        nUser.getSchools().add(school);
	        userRepository.persist(nUser);
	        System.out.println("User Thomas Done !");
	    }

	public void insertProblem() {
		User u = userRepository.getUserByUserName("deli");
		Resource r = resourceRepository.getResourceByName("Français");
		Problem p = new Problem();
		Comment c = new Comment();
		c.setDescription("Commentaire sur le problème numéro 1 :D");
		c.setTitle("Problème 1");
		c.setProblem(p);
		//c.setAuthor(u);
		p.addComment(c);
		p.setResolved(false);
		p.setResource(r);
		//p.setAuthor(u);
		p.setDescription("Problème numéro 1");
		p.setName("Mon problème ^^");
		problemRepository.persist(p);
		commentRepository.persist(c);
		System.out.println("Problem & Comment Done !");
	}
	
	public void insertResource() {
		frDupont = new Resource("Français - Dupont", "cours de français pour débutant écrit par Charlotte Dupont");
		mathGob = new Resource("Goblin-Maths", "Jeu de mathématiques avec des goblins.");
        frOrtho1 = new Resource("Français - Dupond", "cours d orthographe débutant écrit par Charlotte Dupont");
        mathFraction1 = new Resource("Goblin-Fractionnés", "Jeu de mathématiques avec des découpes de goblins.");
		//frDupont.setUser(userRepository.getUserByUserName("deli"));
		//mathGob.setUser(userRepository.getUserByUserName("deli"));
		resourceRepository.persist(frDupont);
		resourceRepository.persist(mathGob);
		resourceRepository.persist(frOrtho1);
        resourceRepository.persist(mathFraction1);
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
	
	public void insertPlayList() {
	    PlayList p = new PlayList("PlayList 1", "");
		p.getResourceList().add(frDupont);
        p.getResourceList().add(mathGob);
        p.getResourceList().add(frOrtho1);
        p.getResourceList().add(mathFraction1);
        playListRepository.persist(p);
		System.out.println("PlayList Done !");
	}
	
	public void insertUrlResource() {
		Resource r1 = resourceRepository.getResourceByName("Français - Dupont");
		Resource r2 = resourceRepository.getResourceByName("Goblin-Math");
		UrlResource url1 = new UrlResource("Url1", "http://url1.com", r1);
		UrlResource url2 = new UrlResource("Url2", "http://url2.com", r2);
		this.urlResourceRepository.persist(url1);
		this.urlResourceRepository.persist(url2);
		System.out.println("Url Done ");
	}
}
