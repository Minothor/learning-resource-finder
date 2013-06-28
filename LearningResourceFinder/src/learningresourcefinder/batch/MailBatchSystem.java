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
public class MailBatchSystem implements Runnable {

    @Autowired MailService ms1;
    @Autowired UserRepository dao;

	public static void main(String[] args) {
		BatchUtil.startSpringBatch(MailBatchSystem.class);
	}

	@Override
	public void run() {
		User uOne = dao.find(1L);

		Mail mOne = new Mail(uOne, "Test slow", MailCategory.USER,
				"HELLO WORLD", MailType.SLOW_NOT_GROUPABLE, true);

		Mail m2 = new Mail(uOne, "Test IMMEDIATE", MailCategory.USER,
				"HELLO WORLD", MailType.IMMEDIATE, true);

		Mail m3 = new Mail(uOne, "Test IMMEDIATE", MailCategory.USER,
				"HELLO WORLD", MailType.IMMEDIATE, false);

		Mail m4 = new Mail(uOne, "Test slow", MailCategory.USER,
				"HELLO WORLD", MailType.SLOW_NOT_GROUPABLE, true);

		Mail m5 = new Mail(uOne, "Test GROUPABLE1", MailCategory.NEWSLETTER,
				"HELLO WORLD", MailType.GROUPABLE, true);

		Mail m6 = new Mail(uOne, "Test GROUPABLE2", MailCategory.CONTACT,
				"HELLO WORLD", MailType.GROUPABLE, true);

		// Mail m7 = new Mail(uOne, "Test GROUPABLE3", MailCategory.CONTACT,
		// "HELLO WORLD", MailType.GROUPABLE, true);

		ms1.sendMail(mOne);

		ms1.sendMail(m2);

		ms1.sendMail(m3);

		ms1.sendMail(m4);

		ms1.sendMail(m5);

		ms1.sendMail(m6);

		// ms1.sendMail(m7);

		try {
            Thread.currentThread().sleep(20 * 60 * 1000); // 20 minutes
		} catch (InterruptedException e) {
            throw new RuntimeException(e);
		}

	}



}
