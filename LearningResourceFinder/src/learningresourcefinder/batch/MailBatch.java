package learningresourcefinder.batch;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.BasicConfigurator;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailBatch implements Runnable {
	
	
	JavaMailSenderImpl myJavaMailSender;
	
	String myHost="smtp.gmail.com"; 
    int myPort=758; 
	
	final private static String toto = "ahmed.idoumhaidi@gmail.com";
	
	public static void main(String[] args){
		
		BatchUtil.startSpringBatch(MailBatch.class);
		
	}
	

	@Override
	public void run() {
		
		
		
	    sendMail();
	    
	    System.out.println("Send Mail");
		
	}
	
	@PostConstruct  // Search for more details
	public void PostConstruct(){
		
		BasicConfigurator.configure();
		
		myJavaMailSender = new JavaMailSenderImpl();
		myJavaMailSender.setProtocol("smtp");
		myJavaMailSender.setHost(myHost);
		myJavaMailSender.setPort(myPort);
		
	}
	
	
	public void sendMail(){
		
		
		MimeMessagePreparator  myMime  = new MimeMessagePreparator(){

			@Override
			public void prepare(final MimeMessage argOne) throws Exception {
				
				MimeMessageHelper myMimeHelper = new MimeMessageHelper(argOne,true,"UTF-8");
				
				myMimeHelper.setTo(toto);
				myMimeHelper.setBcc("Nothing");
				myMimeHelper.setCc("Nothing");
				myMimeHelper.setFrom("thomas.delizee@gmail.com");
				myMimeHelper.setSubject("TestMail");
				myMimeHelper.setText("Bonsoir Emile cava ??");
				
			}
			
		};
		
		myJavaMailSender.send(myMime);
			
	}
	
}
