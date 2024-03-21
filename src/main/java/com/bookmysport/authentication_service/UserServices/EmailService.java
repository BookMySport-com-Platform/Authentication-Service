package com.bookmysport.authentication_service.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendSimpleMail(String to,String text,String subject) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(to);
            mailMessage.setText(text);
            mailMessage.setSubject(subject);

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully to " + to;
        }

        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
