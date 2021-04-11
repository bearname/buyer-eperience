package com.example.restservice.service;

import com.example.restservice.config.Config;
import com.example.restservice.events.BaseApplicationEvent;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailerService {
    private final JavaMailSender mailSender;
//    private static final Logger log = LoggerFactory.getLogger(MailerService.class);

    public MailerService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(BaseApplicationEvent event, String text) {
        try {
            String subject = event.getSubject();
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom(Config.FROM_EMAIL);
            String recipientAddress = event.getSubscription().getUser().getEmail();
            email.setTo(recipientAddress);
            email.setSubject(subject);
            email.setText(text);
            mailSender.send(email);
        } catch (MailException exception) {
            System.out.println("mail service not work. " + exception.getMessage());
        }
    }
}
