package learningresourcefinder.batch;

import javax.annotation.PostConstruct;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailBatchSendSimpleMail implements Runnable {

    JavaMailSenderImpl javamail;

    public static void main(String[] args) {

        BatchUtil.startSpringBatch(MailBatchSendSimpleMail.class);

    }

    @PostConstruct
    public void PostConstructor() {
        javamail = new JavaMailSenderImpl();
        javamail.setProtocol("smtp");
        javamail.setHost("smtp.gmail.com");
        javamail.setPort(587);
        javamail.setUsername("reformyourcountrytest@gmail.com");
        javamail.setPassword("technofutur");

        javamail.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        javamail.getJavaMailProperties().setProperty(
                "mail.smtp.starttls.enable", "true");
    }

    @Override
    public void run() {



        sendMail("jaumottej@gmail.com", "jaumottej@gmail.com", "Test",
                "Bonjour2");

    }

    public void sendMail(String replyTo, String to, String subject, String text) {

        SimpleMailMessage sms = new SimpleMailMessage();

        sms.setReplyTo(replyTo);
        sms.setTo(to);
        sms.setSubject(subject);
        sms.setText(text);

        javamail.send(sms);

        System.out.println("Message envoyé");

    }

}
