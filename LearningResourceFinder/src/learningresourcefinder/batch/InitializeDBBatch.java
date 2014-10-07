package learningresourcefinder.batch;

import java.util.Date;

import learningresourcefinder.model.Comment;
import learningresourcefinder.model.Competence;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Problem;
import learningresourcefinder.model.Rating;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.School;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.AccountStatus;
import learningresourcefinder.model.User.Role;
import learningresourcefinder.repository.CommentRepository;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.PlayListRepository;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.repository.RatingRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.SchoolRepository;
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
//	@Autowired	TaskRepository taskRepository;
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
		admin.setAccountLevel(1);
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
		nUser.setAccountLevel(1);
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
		Competence root = new Competence("root", "Catégories");
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

		Competence l1a = new Competence("L1A", "Repérer les informations relatives aux références d’un livre, d’un texte, d’un document visuel");
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

		Competence l1b = new Competence("L1B", "Choisir un document en fonction du projet et du contexte de l’activité");
		competenceRepository.persist(l1b);
		l1b.bindWithParent(l1);

		Competence l1bp4 = new Competence("L1B-P4", "L1B pour 4e primaire");
		competenceRepository.persist(l1bp4);
		l1bp4.bindWithParent(l1b);

		Competence l1bp2 = new Competence("L1B-P2", "L1B pour 2e primaire");
		competenceRepository.persist(l1bp2);
		l1bp2.bindWithParent(l1b);

		Competence l1c = new Competence("L1C", "Anticiper le contenu d'un document en utilisant ses indices externes et internes");
		competenceRepository.persist(l1c);
		l1c.bindWithParent(l1);

		Competence l1cp4 = new Competence("L1C-P4", "L1C pour 4e primaire");
		competenceRepository.persist(l1cp4);
		l1cp4.bindWithParent(l1c);

		Competence l1cp2 = new Competence("L1C-P2", "L1C pour 2e primaire");
		competenceRepository.persist(l1cp2);
		l1cp2.bindWithParent(l1c);

		Competence l1d = new Competence("L1D", "Saisir l'intention dominante de l'auteur (informer, persuader, enjoindre, émouvoir, donner du plaisir, ...)");
		competenceRepository.persist(l1d);
		l1d.bindWithParent(l1);

		Competence l1dp6 = new Competence("L1D-P6", "L1D pour 6e primaire");
		competenceRepository.persist(l1dp6);
		l1dp6.bindWithParent(l1d);

		Competence l1dp4 = new Competence("L1D-P4", "L1D pour 4e primaire");
		competenceRepository.persist(l1dp4);
		l1dp4.bindWithParent(l1d);

		Competence l1dp2 = new Competence("L1D-P2", "L1D pour 2e primaire");
		competenceRepository.persist(l1dp2);
		l1dp2.bindWithParent(l1d);

		Competence l1e = new Competence("L1E", "Adapter sa stratégie de lecture en fonction du projet, du document et du temps accordé : lecture intégrale ou sélective");
		competenceRepository.persist(l1e);
		l1e.bindWithParent(l1);

		Competence l1ep6 = new Competence("L1E-P6", "L1E pour 6e primaire");
		competenceRepository.persist(l1ep6);
		l1ep6.bindWithParent(l1e);

		Competence l1ep4 = new Competence("L1E-P4", "L1E pour 4e primaire");
		competenceRepository.persist(l1ep4);
		l1ep4.bindWithParent(l1e);

		Competence l1ep2 = new Competence("L1E-P2", "L1E pour 2e primaire");
		competenceRepository.persist(l1ep2);
		l1ep2.bindWithParent(l1e);

		Competence l1f = new Competence("L1F", "Adopter une vitesse de lecture favorisant le traitement de l'information");
		competenceRepository.persist(l1f);
		l1f.bindWithParent(l1);

		Competence l1fp2 = new Competence("L1F-P2", "L1F pour 2e primaire");
		competenceRepository.persist(l1fp2);
		l1fp2.bindWithParent(l1f);

		Competence l1fp4 = new Competence("L1F-P4", "L1F pour 4e primaire");
		competenceRepository.persist(l1fp4);
		l1fp4.bindWithParent(l1f);

		Competence l2 = new Competence("L2", "Elaborer des significations");
		competenceRepository.persist(l2);
		l2.bindWithParent(sl);

		Competence l2a = new Competence("L2A", "Gérer la compréhension du document pour dégager les informations explicites");
		competenceRepository.persist(l2a);
		l2a.bindWithParent(l2);

		Competence l2ap6 = new Competence("L2A-P6", "L2A pour 6e primaire");
		competenceRepository.persist(l2ap6);
		l2ap6.bindWithParent(l2a);

		Competence l2ap4 = new Competence("L2A-P4", "L2A pour 4e primaire");
		competenceRepository.persist(l2ap4);
		l2ap4.bindWithParent(l2a);

		Competence l2ap2 = new Competence("L2A-P2", "L2A pour 2e primaire");
		competenceRepository.persist(l2ap2);
		l2ap2.bindWithParent(l2a);

		Competence l2b = new Competence("L2B", "Gérer la compréhension du document pour découvrir les informations implicites (inférer)");
		competenceRepository.persist(l2b);
		l2b.bindWithParent(l2);

		Competence l2bp6 = new Competence("L2B-P6", "L2B pour 6e primaire");
		competenceRepository.persist(l2bp6);
		l2bp6.bindWithParent(l2b);

		Competence l2bp4 = new Competence("L2B-P4", "L2B pour 4e primaire");
		competenceRepository.persist(l2bp4);
		l2bp4.bindWithParent(l2b);

		Competence l2bp2 = new Competence("L2B-P2", "L2B pour 2e primaire");
		competenceRepository.persist(l2bp2);
		l2bp2.bindWithParent(l2b);

		Competence l2c = new Competence("L2C", "Gérer la compréhension du document pour vérifier des hypothèses émises personnellement ou proposées");
		competenceRepository.persist(l2c);
		l2c.bindWithParent(l2);

		Competence l2cp4 = new Competence("L2C-P4", "L2C pour 4e primaire");
		competenceRepository.persist(l2cp4);
		l2cp4.bindWithParent(l2c);

		Competence l2cp2 = new Competence("L2C-P2", "L2C pour 2e primaire");
		competenceRepository.persist(l2cp2);
		l2cp2.bindWithParent(l2c);

		Competence l2d = new Competence("L2D", "Gérer la compréhension du document pour percevoir le sens global afin de pouvoir restituer l'histoire en ordre chronologique");
		competenceRepository.persist(l2d);
		l2d.bindWithParent(l2);

		Competence l2dp6 = new Competence("L2D-P6", "L2D pour 6e primaire");
		competenceRepository.persist(l2dp6);
		l2dp6.bindWithParent(l2d);

		Competence l2dp4= new Competence("L2D-P4", "L2D pour 4e primaire");
		competenceRepository.persist(l2dp4);
		l2dp4.bindWithParent(l2d);

		Competence l2dp2 = new Competence("L2D-P2", "L2D pour 2e primaire");
		competenceRepository.persist(l2dp2);
		l2dp2.bindWithParent(l2d);

		Competence l2e = new Competence("L2E", "Gérer la compréhension du document pour percevoir le sens global afin de pouvoir reformuler et utiliser les informations");
		competenceRepository.persist(l2e);
		l2e.bindWithParent(l2);

		Competence l2ep6 = new Competence("L2E-P6", "L2E pour 6e primaire");
		competenceRepository.persist(l2ep6);
		l2ep6.bindWithParent(l2e);

		Competence l2ep4 = new Competence("L2E-P4", "L2E pour 4e primaire");
		competenceRepository.persist(l2ep4);
		l2ep4.bindWithParent(l2e);

		Competence l2ep2 = new Competence("L2E-P2", "L2E pour 2e primaire");
		competenceRepository.persist(l2ep2);
		l2ep2.bindWithParent(l2e);

		Competence l2f = new Competence("L2F", "Gérer la compréhension du document pour percevoir le sens global afin de pouvoir reformuler ou exécuter un enchainement de consignes");
		competenceRepository.persist(l2f);
		l2f.bindWithParent(l2);

		Competence l2fp2 = new Competence("L2F-P2", "L2F pour 2e primaire");
		competenceRepository.persist(l2fp2);
		l2fp2.bindWithParent(l2f);

		Competence l2fp4 = new Competence("L2F-P4", "L2F pour 4e primaire");
		competenceRepository.persist(l2fp4);
		l2fp4.bindWithParent(l2f);

		Competence l2g = new Competence("L2G", "Gérer la compréhension du document pour percevoir le sens global afin de pouvoir dégager la thèse et identifier quelques arguments");
		competenceRepository.persist(l2g);
		l2g.bindWithParent(l2);

		Competence l2gp2 = new Competence("L2G-P2", "L2G pour 2e primaire");
		competenceRepository.persist(l2gp2);
		l2gp2.bindWithParent(l2g);

		Competence l2gp4 = new Competence("L2G-P4", "L2G pour 4e primaire");
		competenceRepository.persist(l2gp4);
		l2gp4.bindWithParent(l2g);

		Competence l2h = new Competence("L2H", "Gérer la compréhension du document pour réagir en interaction éventuelle avec d'autres lecteurs et distinguer le réel de l'imaginaire");
		competenceRepository.persist(l2h);
		l2h.bindWithParent(l2);

		Competence l2hp6 = new Competence("L2H-P6", "L2H pour 6e primaire");
		competenceRepository.persist(l2hp6);
		l2hp6.bindWithParent(l2h);

		Competence l2hp4 = new Competence("L2H-P4", "L2H pour 4e primaire");
		competenceRepository.persist(l2hp4);
		l2hp4.bindWithParent(l2h);

		Competence l2hp2 = new Competence("L2H-P2", "L2H pour 2e primaire");
		competenceRepository.persist(l2hp2);
		l2hp2.bindWithParent(l2h);

		Competence l2i = new Competence("L2I", "Gérer la compréhension du document pour réagir en interaction éventuelle avec d'autres lecteurs et distinguer le réel du virtuel");
		competenceRepository.persist(l2i);
		l2i.bindWithParent(l2);

		Competence l2ip4 = new Competence("L2I-P4", "L2I pour 4e primaire");
		competenceRepository.persist(l2ip4);
		l2ip4.bindWithParent(l2i);

		Competence l2ip2 = new Competence("L2I-P2", "L2I pour 2e primaire");
		competenceRepository.persist(l2ip2);
		l2ip2.bindWithParent(l2i);

		Competence l2j = new Competence("L2J", "Gérer la compréhension du document pour réagir en interaction éventuelle le vraisemblable de l'invraisemblable");
		competenceRepository.persist(l2j);
		l2j.bindWithParent(l2);

		Competence l2jp2 = new Competence("L2J-P2", "L2J pour 2e primaire");
		competenceRepository.persist(l2jp2);
		l2jp2.bindWithParent(l2j);

		Competence l2jp4 = new Competence("L2J-P4", "L2J pour 4e primaire");
		competenceRepository.persist(l2jp4);
		l2jp4.bindWithParent(l2j);

		Competence l2k = new Competence("L2K", "Gérer la compréhension du document pour réagir en interaction éventuelle avec d'autres lecteurs et distinguer le vrai du faux");
		competenceRepository.persist(l2k);
		l2k.bindWithParent(l2);

		Competence l2kp2 = new Competence("L2K-P2", "L2K pour 2e primaire");
		competenceRepository.persist(l2kp2);
		l2kp2.bindWithParent(l2k);

		Competence l2kp4 = new Competence("L2K-P4", "L2K pour 4e primaire");
		competenceRepository.persist(l2kp4);
		l2kp4.bindWithParent(l2k);

		Competence l3 = new Competence("L3", "Dégager l’organisation d’un texte");
		competenceRepository.persist(l3);
		l3.bindWithParent(sl);

		Competence l3a = new Competence("L3A", "Reconnaître un nombre diversifié de documents en identifiant la structure dominante narrative");
		competenceRepository.persist(l3a);
		l3a.bindWithParent(l3);

		Competence l3ap2 = new Competence("L3A-P2", "L3A pour 2e primaire");
		competenceRepository.persist(l3ap2);
		l3ap2.bindWithParent(l3a);

		Competence l3ap4 = new Competence("L3A-P4", "L3K pour 4e primaire");
		competenceRepository.persist(l3ap4);
		l3ap4.bindWithParent(l3a);

		Competence l3b = new Competence("L3B", "Reconnaître un nombre diversifié de documents en identifiant la structure dominante descriptive");
		competenceRepository.persist(l3b);
		l3b.bindWithParent(l3);

		Competence l3bp2 = new Competence("L3B-P2", "L3B pour 2e primaire");
		competenceRepository.persist(l3bp2);
		l3bp2.bindWithParent(l3b);

		Competence l3bp4 = new Competence("L3B-P4", "L3B pour 4e primaire");
		competenceRepository.persist(l3bp4);
		l3bp4.bindWithParent(l3b);

		Competence l3c = new Competence("L3C", "Reconnaître un nombre diversifié de documents en identifiant la structure dominante explicative");
		competenceRepository.persist(l3c);
		l3c.bindWithParent(l3);

		Competence l3cp2 = new Competence("L3C-P2", "L3C pour 2e primaire");
		competenceRepository.persist(l3cp2);
		l3cp2.bindWithParent(l3c);

		Competence l3cp4 = new Competence("L3C-P4", "L3C pour 4e primaire");
		competenceRepository.persist(l3cp4);
		l3cp4.bindWithParent(l3c);

		Competence l3d = new Competence("L3D", "Reconnaître un nombre diversifié de documents en identifiant la structure dominante argumentative");
		competenceRepository.persist(l3d);
		l3d.bindWithParent(l3);

		Competence l3dp2 = new Competence("L3D-P2", "L3D pour 2e primaire");
		competenceRepository.persist(l3dp2);
		l3dp2.bindWithParent(l3d);

		Competence l3dp4 = new Competence("L3D-P4", "L3D pour 4e primaire");
		competenceRepository.persist(l3dp4);
		l3dp4.bindWithParent(l3d);

		Competence l3e = new Competence("L3E", "Reconnaître un nombre diversifié de documents en identifiant la structure dialoguée");
		competenceRepository.persist(l3e);
		l3e.bindWithParent(l3);

		Competence l3ep6 = new Competence("L3E-P6", "L3E pour 6e primaire");
		competenceRepository.persist(l3ep6);
		l3ep6.bindWithParent(l3e);

		Competence l3ep2 = new Competence("L3E-P2", "L3E pour 2e primaire");
		competenceRepository.persist(l3ep2);
		l3ep2.bindWithParent(l3e);

		Competence l3ep4 = new Competence("L3E-P4", "L3E pour 4e primaire");
		competenceRepository.persist(l3ep4);
		l3ep4.bindWithParent(l3e);

		Competence l3f = new Competence("L3F", "Repérer les marques de l'organisation générale : paragraphes, alinéas, ...");
		competenceRepository.persist(l3f);
		l3f.bindWithParent(l3);

		Competence l3fp2 = new Competence("L3F-P2", "L3F pour 2e primaire");
		competenceRepository.persist(l3fp2);
		l3fp2.bindWithParent(l3f);

		Competence l3fp4 = new Competence("L3F-P4", "L3F pour 4e primaire");
		competenceRepository.persist(l3fp4);
		l3fp4.bindWithParent(l3f);

		Competence l3g = new Competence("L3G", "Repérer les marques de l'organisation générale : mise en page");
		competenceRepository.persist(l3g);
		l3g.bindWithParent(l3);

		Competence l3gp2 = new Competence("L3G-P2", "L3G pour 2e primaire");
		competenceRepository.persist(l3gp2);
		l3gp2.bindWithParent(l3g);

		Competence l3gp4 = new Competence("L3G-P4", "L3G pour 4e primaire");
		competenceRepository.persist(l3gp4);
		l3gp4.bindWithParent(l3g);

		Competence l3h = new Competence("L3H", "Repérer les marques de l'organisation générale : organisateurs textuels");
		competenceRepository.persist(l3h);
		l3h.bindWithParent(l3);

		Competence l3hp6 = new Competence("L3H-P6", "L3H pour 6e primaire");
		competenceRepository.persist(l3hp6);
		l3hp6.bindWithParent(l3h);

		Competence l3hp2 = new Competence("L3H-P2", "L3H pour 2e primaire");
		competenceRepository.persist(l3hp2);
		l3hp2.bindWithParent(l3h);

		Competence l3hp4 = new Competence("L3H-P4", "L3H pour 4e primaire");
		competenceRepository.persist(l3hp4);
		l3hp4.bindWithParent(l3h);

		Competence l3i = new Competence("L3I", "Repérer les marques de l'organisation générale : modes et temps verbaux");
		competenceRepository.persist(l3i);
		l3i.bindWithParent(l3);

		Competence l3ip6 = new Competence("L3I-P6", "L3I pour 6e primaire");
		competenceRepository.persist(l3ip6);
		l3ip6.bindWithParent(l3i);

		Competence l3ip2 = new Competence("L3I-P2", "L3I pour 2e primaire");
		competenceRepository.persist(l3ip2);
		l3ip2.bindWithParent(l3i);

		Competence l3ip4 = new Competence("L3I-P4", "L3I pour 4e primaire");
		competenceRepository.persist(l3ip4);
		l3ip4.bindWithParent(l3i);

		Competence l4 = new Competence("L4", "Percevoir la cohérence entre phrases et groupes de phrases tout au long du texte");
		competenceRepository.persist(l4);
		l4.bindWithParent(sl);

		Competence l5 = new Competence("L5", "Tenir compte des unités grammaticales");
		competenceRepository.persist(l5);
		l5.bindWithParent(sl);

		Competence l6 = new Competence("L6", "Traiter les unités lexicales");
		competenceRepository.persist(l6);
		l6.bindWithParent(sl);

		Competence l7 = new Competence("L7", "Percevoir les interactions entre les éléments verbaux et non verbaux");
		competenceRepository.persist(l7);
		l7.bindWithParent(sl);

		Competence se = new Competence("FF-SE", "Savoir Ecrire");
		competenceRepository.persist(se);
		se.bindWithParent(ff);

		Competence e1 = new Competence("E1", "Orienter son écrit en fonction de la situation de communication");
		competenceRepository.persist(e1);
		e1.bindWithParent(se);

		Competence e2 = new Competence("E2", "Elaborer des contenus");
		competenceRepository.persist(e2);
		e2.bindWithParent(se);

		Competence e3 = new Competence("E3", "Assurer l’organisation et la cohérence du texte");
		competenceRepository.persist(e3);
		e3.bindWithParent(se);

		Competence e4 = new Competence("E4", "Utiliser les unités grammaticales et lexicales");
		competenceRepository.persist(e4);
		e4.bindWithParent(se);

		Competence e5 = new Competence("E5", "Assurer la présentation");
		competenceRepository.persist(e5);
		e5.bindWithParent(se);

		Competence sp = new Competence("FF-SP", "Savoir Parler");
		competenceRepository.persist(sp);
		sp.bindWithParent(ff);

		Competence sp1 = new Competence("SP1", "Orienter sa parole en fonction de la situation de communication");
		competenceRepository.persist(sp1);
		sp1.bindWithParent(sp);

		Competence sp2 = new Competence("SP2", "Elaborer des significations");
		competenceRepository.persist(sp2);
		sp2.bindWithParent(sp);

		Competence sp3 = new Competence("SP3", "Assurer et dégager l’organisation et la cohérence du message");
		competenceRepository.persist(sp3);
		sp3.bindWithParent(sp);

		Competence sp4 = new Competence("SP4", "Utiliser les moyens non verbaux");
		competenceRepository.persist(sp4);
		sp4.bindWithParent(sp);

		Competence sec = new Competence("FF-SEC", "Savoir Ecouter");
		competenceRepository.persist(sec);
		sec.bindWithParent(ff);

		Competence ec1 = new Competence("EC1", "Orienter son écoute en fonction de la situation de communication");
		competenceRepository.persist(ec1);
		ec1.bindWithParent(sec);

		Competence ec2 = new Competence("EC2", "Elaborer des significations");
		competenceRepository.persist(ec2);
		ec2.bindWithParent(sec);

		Competence ec3 = new Competence("EC3", "Dégager l’organisation et la cohérence du message");
		competenceRepository.persist(ec3);
		ec3.bindWithParent(sec);

		Competence ec4 = new Competence("EC4", "Identifier les moyens non verbaux");
		competenceRepository.persist(ec4);
		ec4.bindWithParent(sec);

		Competence math = new Competence("FM", "Math");
		competenceRepository.persist(math);
		math.bindWithParent(socle);

		Competence n = new Competence("MATH-NBR", "Nombres");
		competenceRepository.persist(n);
		n.bindWithParent(math);

		Competence n311 = new Competence("N311", "Compter, dénombrer, classer");
		competenceRepository.persist(n311);
		n311.bindWithParent(n);

		Competence n312 = new Competence("N312", "Organiser les nombres par familles");
		competenceRepository.persist(n312);
		n312.bindWithParent(n);

		Competence n313 = new Competence("N313", "Calculer");
		competenceRepository.persist(n313);
		n313.bindWithParent(n);

		Competence sf = new Competence("MATH-SF", "Solides et Figures");
		competenceRepository.persist(sf);
		sf.bindWithParent(math);

		Competence sf321 = new Competence("SF321", "Répérer");
		competenceRepository.persist(sf321);
		sf321.bindWithParent(sf);

		Competence sf322 = new Competence("SF322", "Reconnaitre, comparer, construire, exprimer");
		competenceRepository.persist(sf322);
		sf322.bindWithParent(sf);

		Competence sf323 = new Competence("SF323", "Dégager des régularités, des propriétés, argumenter");
		competenceRepository.persist(sf323);
		sf323.bindWithParent(sf);

		Competence g = new Competence("MATH-GRD", "Grandeurs");
		competenceRepository.persist(g);
		g.bindWithParent(math);

		Competence g331 = new Competence("G331", "Comparer, mesurer");
		competenceRepository.persist(g331);
		g331.bindWithParent(g);

		Competence g332 = new Competence("G332", "Opérer, fractionner");
		competenceRepository.persist(g332);
		g332.bindWithParent(g);

		Competence td = new Competence("MATH-TD", "Traitement des Données");
		competenceRepository.persist(td);
		td.bindWithParent(math);

		Competence td34 = new Competence("TD34", "Traitement des données");
		competenceRepository.persist(td34);
		td34.bindWithParent(td);

		Competence term = new Competence("term", "Terminales");
		competenceRepository.persist(term);
		term.bindWithParent(root);

		Competence tf = new Competence("TF", "Français (terminale)");
		competenceRepository.persist(tf);
		tf.bindWithParent(term);

		frDupont.getCompetences().add(l1ap4);

		mathGob.getCompetences().add(math);

		System.out.println("Competences done!");
	}

	public void insertPlayList() {
		PlayList p = new PlayList("PlayList 1", "");
		p.getResources().add(frDupont);
		p.getResources().add(mathGob);
		p.getResources().add(frOrtho1);
		p.getResources().add(mathFraction1);
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
