package com.projectx.UI;

import com.projectx.controllers.UserController;
import com.projectx.models.User;
import com.projectx.repositories.UserDaoExtension;
import com.projectx.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Properties;

@Service
public class Account {
    private UserService userService=new UserService(new UserDaoExtension());
    private final UserController controller=new UserController(userService);

    /**
        This function is designed to be interacted with from the front end with user account creation.
        After the user clicks on the button, this function gets called.
    */

    public static void main(String[] args) throws MessagingException, NamingException {
        Account account=new Account();
        System.out.println(account.addUser("18xxperson@gmail.com","Rockband","Jonathan"));
        account.verifyEmail("18xxperson@gmail.com");
        System.out.println(account.forgotPassword("18xxperson@gmail.com"));
        account.UpdatePassword("Rockband3","18xxperson@gmail.com");
    }

    public String addUser(String email,String password,String name)
    {
         User user=new User();
         user.setEmail(email);
         user.setPassword(password);
         user.setName(name);
         ResponseEntity<String> response =controller.createUser(user);
         return response.getBody();
    }
    public String forgotPassword(String email) throws MessagingException {
        ResponseEntity<User> response=controller.getUser(email);
        if(response.getBody()==null)
        {
            return "There is no user with this email";
        }
        String message="Click on this link to reset your password";
        Properties properties=System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true"); //enable authentication
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Authenticator authenticator=new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email,response.getBody().getPassword());
            }
        };
        Session session = Session.getInstance(properties,authenticator);
        Message msg = new MimeMessage(session);
        msg.setFrom();
        msg.setText(message);
        msg.setSentDate(new Date());
        msg.setSubject("Resetting Password");
        msg.setRecipient(Message.RecipientType.TO,InternetAddress.parse(response.getBody().getEmail())[0]);
        Transport.send(msg);
        return "The email has been sent";
    }

    public void UpdatePassword(String password,String email)
    {
        ResponseEntity<User> response=controller.getUser(email);
        User user=response.getBody();
        assert user != null;
        user.setPassword(password);
        HttpServletRequest request= new HttpRequestHandler();
        controller.editUser(user,request);
    }


    public void verifyEmail(String email)
    {
        ResponseEntity<User> response=controller.getUser(email);
        User user=response.getBody();
        if(user!=null)
           user.setEmail_verified(true);
        HttpServletRequest request= new HttpRequestHandler();
        controller.editUser(user,request);
    }

}
