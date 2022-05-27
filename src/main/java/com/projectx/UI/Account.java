package com.projectx.UI;

import com.projectx.controllers.UserController;
import com.projectx.models.User;
import org.springframework.http.ResponseEntity;

public class Account {
    private static UserController controller;
    /**
        This function is designed to be interacted with from the front end.
        After the user clicks on the button, this function gets called.
    */
    public static String addUser(String email,String password,
                                 String name,String birthdate,String nickname,String phone,
                                 String given_name,String family_name,String picture)
    {
         User user=new User(0,
                 birthdate,email,false,given_name,family_name,name,
                 nickname,phone,false,picture,password);
         ResponseEntity<String> response =controller.createUser(user);
         return response.getBody();
    }
    public static String forgotPassword(String email)
    {
        ResponseEntity<User> response=controller.getUser(email);
        if(response.getBody()==null)
        {
            return "There is no user with this email";
        }
        String message="Click on this link to reset your password";
        return "The email has been sent";
    }
}
