package com.projectx.utility;

import com.projectx.controllers.UserController;

public class Testing {
    public static void main(String[] args)
    {
        UserController userController=new UserController();
        userController.sendEmail("18xxperson@gmail.com","reset password","Click on this link to reset the password");
    }
}
