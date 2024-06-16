package com.example.emailsender.emailsender.service;

import com.example.emailsender.emailsender.model.sendEmail;
import com.example.emailsender.emailsender.repository.sendEmailRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class sendEmailService {

    @Autowired
    sendEmailRepo sendemailrepo;

    private final JavaMailSender mailSender;
    private final String sender;

    @Autowired
    public sendEmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String sender) {
        this.mailSender = mailSender;
        this.sender = sender;
    }
    public ResponseEntity<String> sendEmailWithAttachment(
            String toAddress, String subject, String body, MultipartFile attachment) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);

            mimeMessageHelper.addAttachment(attachment.getOriginalFilename(), attachment);

            mailSender.send(mimeMessage);
            return ResponseEntity.ok("Email has been sent successfully...");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while sending email!!!");
        }
    }

    public ResponseEntity<String> sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        return new ResponseEntity<>("Email sent", HttpStatus.OK);
    }

    public ResponseEntity<String> addEmail(String subject, String body, String toAddress){
        sendEmail sendEmail = new sendEmail();
        sendEmail.setSubject(subject);
        sendEmail.setBody(body);
        sendEmail.setToAddress(toAddress);

        sendemailrepo.save(sendEmail);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public void sendEmailToAll() {
        List<sendEmail> emails = sendemailrepo.findAll();

        for (sendEmail email : emails) {
            String toAddress = email.getToAddress();
            String subject = email.getSubject();
            String body = email.getBody();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(toAddress);
            message.setSubject(subject);
            message.setText(body);

            try {
                mailSender.send(message);
            } catch (MailException e) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while sending email!!!");
                return;
            }
        }
        new ResponseEntity<>("Emails sent to all addresses", HttpStatus.OK);
    }

}