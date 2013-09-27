package learningresourcefinder.batch;

import java.util.Date;


import learningresourcefinder.model.Comment;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Problem;
import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Rating;
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
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.RatingRepository;
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

	@Autowired	ProblemRepository problemRepository;
	@Autowired	UserRepository userRepository;
	@Autowired	CommentRepository commentRepository;
	@Autowired	ResourceRepository resourceRepository;
	@Autowired	SchoolRepository schoolRepository;
	@Autowired	CompetenceRepository competenceRepository;
	@Autowired	TaskRepository taskRepository;
	@Autowired	PlayListRepository playListRepository;
	@Autowired	UrlResourceRepository urlResourceRepository;
	@Autowired	RatingRepository ratingRepository;
	
	Resource frDupont;
	Resource mathGob;
	Resource frOrtho1;
	Resource mathFraction1;	

	School school;  // Various methods need the created school
	
	User admin;
	User nUser;

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
		insertCompetences();
		insertTask();
		insertPlayList();
		insertUrlResource();
		insertRating();
		System.out.println("DataBase Initialized !");
	}

	public void insertRating() {
		Rating  rating = new  Rating();
		rating.setResource(frDupont);
		rating.setUser(admin);
		rating.setScore(5.0);
		ratingRepository.persist(rating);
		System.out.println("rating Done !");
		
		Rating  rating0 = new  Rating();
		rating0.setResource(frDupont);
		rating0.setUser(nUser);
		rating0.setScore(3.0);
		ratingRepository.persist(rating0);
		System.out.println("rating 0 Done !");
		
		Rating  rating1 = new  Rating();
		rating1.setResource(mathGob);
		rating1.setUser(nUser);
		rating1.setScore(4.0);
		ratingRepository.persist(rating1);
		System.out.println("rating 1 Done !");
		
		Rating  rating2 = new  Rating();
		rating2.setResource(mathGob);
		rating2.setUser(admin);
		rating2.setScore(5.0);
		ratingRepository.persist(rating2);
		System.out.println("rating 2 Done !");
		
		Rating  rating3 = new  Rating();
		rating3.setResource(mathGob);
		rating3.setUser(admin);
		rating3.setScore(3.0);
		ratingRepository.persist(rating3);
		System.out.println("rating 3 Done !");
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
		admin = new User();
		admin.setFirstName("Admin");
		admin.setLastName("");
		admin.setBirthDate(new Date());
		admin.setMail("info@lrf.be");
		admin.setAccountStatus(AccountStatus.ACTIVE);
		admin.setUserName("admin");
		admin.setRole(Role.ADMIN);
		admin.hasAdminPrivileges();
		admin.setPassword(SecurityUtils.md5Encode("aaaa"));
		userRepository.persist(admin);
		
       


		// Normal user
		nUser = new User();
		nUser.setFirstName("Thomas");
		nUser.setLastName("Delizee");
		nUser.setBirthDate(new Date());
		nUser.setMail("thomasdelizee@gmail.com");
		nUser.setValidationCode("2fd5f4d5f4d5f4d5f4");
		nUser.setAccountStatus(AccountStatus.ACTIVE);
		nUser.setConsecutiveFailedLogins(0);
		nUser.setNlSubscriber(false);   
		nUser.setPicture(false);
		nUser.setSpammer(false);
		nUser.setUserName("deli");
		nUser.getSchools().add(school);
		userRepository.persist(nUser);
		System.out.println("User Thomas Done !");
	}

	public void insertProblem() {
//		User u = userRepository.getUserByUserName("deli");
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
		frDupont = new Resource("Français - Dupont", "cours de français pour débutant écrit par Charlotte Dupont", admin);
		mathGob = new Resource("Goblin-Maths", "Jeu de mathématiques avec des goblins.", admin);
		frOrtho1 = new Resource("Français - Dupond", "cours d orthographe débutant écrit par Charlotte Dupont", admin);
		mathFraction1 = new Resource("Goblin-Fractionnés", "Jeu de mathématiques avec des découpes de goblins.", admin);
		//frDupont.setUser(userRepository.getUserByUserName("deli"));
		//mathGob.setUser(userRepository.getUserByUserName("deli"));
		resourceRepository.persist(frDupont);
		resourceRepository.persist(mathGob);
		resourceRepository.persist(frOrtho1);
		resourceRepository.persist(mathFraction1);
		System.out.println("Resource Done !");
	}

	public void insertCompetences() {
		
		//Main tree node with null parent
		Competence root = new Competence("root", "Compétences");
		competenceRepository.persist(root);
		//===============================
		
		Competence socle = new Competence("socle", "Fondamental");
		competenceRepository.persist(socle);
		socle.bindWithParent(root);

		Competence ff = new Competence("FF", "Français");
		competenceRepository.persist(ff);
	    ff.bindWithParent(socle);

		Competence sl = new Competence("FF-SL", "Savoir Lire");
		competenceRepository.persist(sl);
		sl.bindWithParent(ff);

		Competence l1 = new Competence("L1", "Orienter sa lecture en fonction de la situation de communication");
		competenceRepository.persist(l1);
		l1.bindWithParent(sl);

		Competence l1a = new Competence("L1A", "Repérer les informations relatives aux références d’un livre, d’un texte, d’un document visuel.");
		competenceRepository.persist(l1a);
		l1a.bindWithParent(l1);

		Competence l1ap2 = new Competence("L1A-P2", "L1A pour 2e primaire");
		competenceRepository.persist(l1ap2);
		l1ap2.bindWithParent(l1a);

		Competence l1ap4 = new Competence("L1A-P4", "L1A pour 4e primaire");
		competenceRepository.persist(l1ap4);
		l1ap4.bindWithParent(l1a);

		Competence l1ap6 = new Competence("L1A-P6", "L1A pour 6e primaire");
		competenceRepository.persist(l1ap6);
		l1ap6.bindWithParent(l1a);

		Competence l1b = new Competence("L1B", "Choisir un document en fonction du projet et du contexte de l’activité.");
		competenceRepository.persist(l1b);
		l1b.bindWithParent(l1);

		Competence l2 = new Competence("L2", "Elaborer des significations");
		competenceRepository.persist(l2);
		l2.bindWithParent(sl);

		Competence l3 = new Competence("L3", "Dégager l’organisation d’un texte");
		competenceRepository.persist(l3);
		l3.bindWithParent(sl);

		Competence math = new Competence("FM", "Math");
		competenceRepository.persist(math);
		math.bindWithParent(socle);
		
		Competence term = new Competence("term", "Terminales");
		competenceRepository.persist(term);
		term.bindWithParent(root);
		
		Competence tf = new Competence("TF", "Français (terminale)");
		competenceRepository.persist(tf);
		tf.bindWithParent(term);
		

		l1ap4.addResource(frDupont);

		math.addResource(mathGob);

		System.out.println("Competences done!");
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
