package learningresourcefinder.batch;

import learningresourcefinder.mail.MailCategory;
import learningresourcefinder.mail.MailType;
import learningresourcefinder.model.Mail;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.MailRepository;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailSystem implements Runnable {

    @Autowired public UserRepository dao;
	@Autowired public MailService mailservice;
	@Autowired public MailRepository mrep;

	public static void main(String[] args) {
		BatchUtil.startSpringBatch(MailSystem.class);

	}

	@Override
	public void run() {

		User uOne = dao.find(1L);

		Mail mOne = new Mail(uOne, "Test", MailCategory.USER,
				"HELLO WORLD", MailType.GROUPABLE, true);

		mOne.setEmailTarget("ahmed.idoumhaidi@gmail.com");

		mailservice.sendMail(mOne);

		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

}
