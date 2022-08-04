package com.projectx.controllers;

import com.projectx.models.Mail;
import com.projectx.models.User;
import com.projectx.services.MailService;
import com.projectx.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;


@RestController("mailController")
public class MailController {
   @Autowired private MailService mailService;

    @PostMapping("recover")
    public ResponseEntity<String> recoverPassword(@PathVariable String email)
    {
        Mail mail=mailService.recoverPassword(email);
        if(mail==null)
        {
            return new ResponseEntity<>("The email is incorrect",HttpStatus.NOT_FOUND);
        }
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
        return new ResponseEntity<>("Your registration information has been sent", HttpStatus.ACCEPTED);
    }
}

