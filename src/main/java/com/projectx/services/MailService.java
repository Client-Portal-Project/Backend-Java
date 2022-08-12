package com.projectx.services;

import com.projectx.models.Mail;
import com.projectx.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service("mailService")
public class MailService {
    @Autowired private UserService userService;
    public Mail recoverPassword(int userid)
    {
        User user=userService.findUserById(userid);
        if (user==null)
        {
            return null;
        }
        user.setPassword("xcnvjkpqwhyr819");
        userService.editUser(user);
        Mail mail=new Mail();
        mail.setSendToEmail(user.getEmail());
        mail.setSubject("Recover Password");
        mail.setSenderPassword("ovilmpbewocdmwjz");
        mail.setMessage("Hello "+user.getFirstName()+"Your new password is "+user.getPassword());
        mail.setFromEmail("18xxperson@gmail.com");
        return mail;
    }

    public Mail register(String email)
    {
        User user= userService.findUserByEmail(email);
        if (user==null)
        {
            return null;
        }
        Mail mail=new Mail();
        mail.setSendToEmail(user.getEmail());
        mail.setSubject("Register User");
        mail.setSenderPassword("ovilmpbewocdmwjz");
        mail.setMessage("Hello "+user.getFirstName()+"Your current password is "+user.getPassword());
        mail.setFromEmail("18xxperson@gmail.com");
        return mail;
    }


}
