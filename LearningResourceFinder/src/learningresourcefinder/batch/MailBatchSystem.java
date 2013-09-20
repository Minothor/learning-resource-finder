package learningresourcefinder.batch;

import learningresourcefinder.exception.UserAlreadyExistsException;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.service.MailService;
import learningresourcefinder.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailBatchSystem implements Runnable {

    @Autowired MailService ms1;
    @Autowired UserRepository dao;
    @Autowired UserService userService;

	public static void main(String[] args) {
		BatchUtil.startSpringBatch(MailBatchSystem.class);
	}

	@Override
	public void run() {
		try {
			userService.registerUser(false, "ibrahimas","123456", "ibrahim.yock@gmail.com");
			
			
		} catch (UserAlreadyExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		try {
            Thread.currentThread().sleep(20 * 600 * 1000); // 20 minutes
		} catch (InterruptedException e) {
            throw new RuntimeException(e);
		}

	}

}
