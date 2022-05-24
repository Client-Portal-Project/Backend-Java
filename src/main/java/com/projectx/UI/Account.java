package com.projectx.UI;

import com.projectx.controllers.UserController;
import com.projectx.models.User;
import org.springframework.http.ResponseEntity;

public class Account {
    /**
        This function is designed to be interacted with from the front end.
        After the user clicks on the button, this function gets called.
    */
    public static String addUser(String email,String password,String name)
    {
        UserController controller=new UserController();
         User user=new User(0,
                 null,email,false,null,null,name,
                 null,null,false,null,password);
         ResponseEntity<String> response =controller.createUser(user);
         return response.getBody();
    }
}
