package com.projectx.controllers;

import com.projectx.models.Mail;
import com.projectx.models.User;
import com.projectx.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


@RestController("mailController")
@RequestMapping("mail")
public class MailController {
   @Autowired private MailService mailService;

    @PostMapping("recover/{email}")
    public ResponseEntity<String> recoverPassword(@PathVariable String email)
    {
        Mail mail=mailService.recoverPassword(email);
        if(mail==null)
        {
            return new ResponseEntity<>("The email is incorrect",HttpStatus.NOT_FOUND);
        }
        sendEmail(mail);
        return new ResponseEntity<>("Your new password has been sent", HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<String> register(User user)
    {
        Mail mail=mailService.register(user);
        if(mail==null)
        {
            return new ResponseEntity<>("No user here",HttpStatus.BAD_REQUEST);
        }
        sendEmail(mail);
        return new ResponseEntity<>("Your registration information has been sent", HttpStatus.ACCEPTED);
    }
    @Bean
    public Mail sendEmail(Mail mail)
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port",587);
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.ssl.protocols","TLSv1.2");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //The password must be an app specific password with the gmail smtp server
                return new PasswordAuthentication(mail.getFromEmail(),mail.getSenderPassword());
            }
        });
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom();
            msg.setRecipients(Message.RecipientType.TO,
                    mail.getSendToEmail());
            msg.setSubject(mail.getSubject());
            msg.setSentDate(new Date());
            if(mail.getMessage()!=null)
               msg.setText(mail.getMessage());
            Transport.send(msg);
        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
        }
        return mail;
    }
}

