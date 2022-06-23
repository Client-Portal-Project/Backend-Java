package com.projectx.UI;

import com.projectx.controllers.UserController;
import com.projectx.models.User;
import com.projectx.repositories.UserDaoExtension;
import com.projectx.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

@Service
public class Account {
    private final UserService userService=new UserService(new UserDaoExtension());
    private final UserController controller=new UserController(userService);

    public Account() throws SQLException {
    }

    /**
        This function is designed to be interacted with from the front end with user account creation.
        After the user clicks on the button, this function gets called.
    */

    public String addUser(String email,String password,String name)
    {
         User user=new User();
         user.setEmail(email);
         user.setPassword(password);
         user.setName(name);
         ResponseEntity<String> response =controller.createUser(user);
         return response.getBody();
    }
    public String forgotPassword(String email) {
        ResponseEntity<User> response=controller.getUser(email);
        User user=response.getBody();
        if(user==null)
        {
            return "The email you entered is incorrect";
        }
        String message="Click on this link to reset your password";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.from", "me@example.com");
        Session session = Session.getInstance(props, null);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom();
            msg.setRecipients(Message.RecipientType.TO,
                    user.getEmail());
            msg.setSubject("Resetting password");
            msg.setSentDate(new Date());
            msg.setText(message);
            Transport.send(msg);
        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
            return "Failed to send message";
        }
        return "message sent";
    }

    public void UpdatePassword(String password,String email)
    {
        ResponseEntity<User> response=controller.getUser(email);
        User user=response.getBody();
        if(user!=null) {
            user.setPassword(password);
            HttpServletRequest request = new HttpRequestHandler();
            controller.editUser(user, request);
        }
    }


    public void verifyEmail(String email)
    {
        ResponseEntity<User> response=controller.getUser(email);
        User user=response.getBody();
        if(user!=null)
           user.setEmail_verified(true);
        HttpServletRequest request= new HttpRequestHandler();
        if(user!=null)
          controller.editUser(user,request);
    }

}
