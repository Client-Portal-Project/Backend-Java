package com.projectx.UI;

import com.projectx.controllers.UserController;
import com.projectx.models.User;
import org.springframework.http.ResponseEntity;

public class Account {
    /**
        This function is designed to be interacted with from the front end.
        After the user clicks on the button, this function gets called.
    */
    public static String addUser(String email,String password,
                                 String name,String birthdate,String nickname,String phone,
                                 String given_name,String family_name,String picture)
    {
        UserController controller=new UserController();
         User user=new User(0,
                 birthdate,email,false,given_name,family_name,name,
                 nickname,phone,false,picture,password);
         ResponseEntity<String> response =controller.createUser(user);
         return response.getBody();
    }
}
