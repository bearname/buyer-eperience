package com.example.restservice.app.service;

import com.example.restservice.app.events.BaseApplicationEvent;
import com.example.restservice.inrostructure.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class JavaMailSenderMailerService implements MailService {
    private final JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger("logger");

    public JavaMailSenderMailerService(JavaMailSender mailSender) {
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
            log.warn("mail service not work. {}", exception.getMessage());
        }
    }
}
