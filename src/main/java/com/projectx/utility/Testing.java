package com.projectx.utility;

import com.projectx.controllers.UserController;

public class Testing {
    public static void main(String[] args)
    {
        UserController userController=new UserController();
        userController.sendEmail("18xxperson@gmail.com","Creating account","Your password for logging in is nklxvcjkl;aewrjio[");
    }
}
