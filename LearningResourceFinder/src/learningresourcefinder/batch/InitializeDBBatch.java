package learningresourcefinder.batch;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class InitializeDBBatch implements Runnable
{
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
	
	public static void main(String[] args) {
		BatchUtil.startSpringBatch(InitializeDBBatch.class);
	}

	@Override
	public void run() {
		insertSchool();
		insertUser();
		insertProblem();
		insertProgramPoints();
		System.out.println("DataBase Initialized !");
	}
	
	public void insertSchool() {
		School sc = new School();
		sc.setAddress("Rue du village 157, 5352 Perwez");
		sc.setCountry("Belgique");
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
		System.out.println("Problem & Comment Done !");
	}
	
	public void insertResource() {
		Resource r1 = new Resource();
		Resource r2 = new Resource();
		r1.setDescription("cours de français pour débutant");
		r1.setTitle("Français");
		r1.setUser(userRepository.getUserByUserName("tato"));
		r2.setDescription("cours de mathématiques");
		r2.setTitle("Maths");
		r2.setUser(userRepository.getUserByUserName("toto"));
		resourceRepository.persist(r1);
		resourceRepository.persist(r2);
		System.out.println("Resource Done !");
	}
	
	public void insertProgramPoints() {
		ProgramPoint pFond = new ProgramPoint("Fondamental", "Enseignement fondamental (primaire)");
		programPointRepository.persist(pFond);

		ProgramPoint p1 = new ProgramPoint("1e primaire", "La première année de l'enseignement fondamental");
		programPointRepository.persist(p1);
		
		ProgramPoint p2 = new ProgramPoint("2e primaire", "La dernière année du 1er degré fondamental");
		programPointRepository.persist(p2);
		
		ProgramPoint p1M = new ProgramPoint("Math", "Mathématiques pour les petits de 6 ans");
		programPointRepository.persist(p1M);

		ProgramPoint p1M1 = new ProgramPoint("Numération", "Dénombrer, compter");
		programPointRepository.persist(p1M1);

		ProgramPoint p1M2 = new ProgramPoint("Addition", "Additions simples avec un seul chiffre");
		programPointRepository.persist(p1M2);
		
		ProgramPoint p1F = new ProgramPoint("Français", "Français pour les petits de 6 ans");
		programPointRepository.persist(p1F);

		pFond.addChild(p1);
		p1.addChild(p1M);
		p1M.addChild(p1M1);
		p1M.addChild(p1M2);
		p1.addChild(p1F);
	}


}
