package learningresourcefinder.batch;

import java.util.Date;

import learningresourcefinder.mail.MailCategory;
import learningresourcefinder.mail.MailType;
import learningresourcefinder.model.Mail;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.AccountStatus;
import learningresourcefinder.repository.MailRepository;
import learningresourcefinder.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldBatch implements Runnable {

	@Autowired
	UserRepository userRepository;
	@Autowired
	MailRepository mailRepository;
	
	public static void main(String[] args) {
		BatchUtil.startSpringBatch(HelloWorldBatch.class);
	}
	
	@Override
	public void run() {
		System.out.println("Hello World");
	}
}
