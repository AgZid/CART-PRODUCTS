package productCart.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JavaMailSender mailSender;

    void sendEmail(String mailContent) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("az.javamokymai@gmail.com");
            helper.setTo("az.javamokymai@gmail.com");
            helper.setSubject("Cart & Product service: Cart was removed");
            helper.setText(mailContent);

            FileSystemResource file = new FileSystemResource("cart.json");
            helper.addAttachment(file.getFilename(), file);

        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        mailSender.send(message);
    }
}
