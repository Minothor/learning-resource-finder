package learningresourcefinder.batch;

import java.util.Date;

import learningresourcefinder.model.User;
import learningresourcefinder.model.User.AccountStatus;
import learningresourcefinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBatch implements Runnable
{
	@Autowired
	UserRepository userRepository;
	
	User u = new User();
	
	public static void main(String[] args) {
		BatchUtil.startSpringBatch(UserBatch.class);
	}

	@Override
	public void run() {
		insertUser();
		//User u1 = userRepository.getUserByUserName("tato");
		//System.out.println(u1.getFirstName());
		System.out.println(u.getFirstName());
	}
	
	public void insertUser() {	
		u.setFirstName("titi");
		u.setLastName("tutu");
		u.setBirthDate(new Date());
		u.setMail("toto2@tata.com");
		u.setValidationCode("2fd5f4d5f4d5f4d5f4");
		u.setAccountStatus(AccountStatus.ACTIVE);
		u.setConsecutiveFailedLogins(0);
		u.setPasswordKnownByTheUser(true);
		u.setNlSubscriber(false);	
		u.setPicture(false);
		u.setSpammer(false);
		u.setUserName("tato");
		userRepository.persist(u);
	}

}
