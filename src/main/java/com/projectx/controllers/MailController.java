package com.projectx.controllers;

import com.projectx.services.MailService;
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
        return new ResponseEntity<>("Your new password has been sent", HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<String> register()
    {
        return new ResponseEntity<>("Your registration information has been sent", HttpStatus.ACCEPTED);
    }
}

