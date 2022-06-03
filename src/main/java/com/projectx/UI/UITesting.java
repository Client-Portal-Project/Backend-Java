package com.projectx.UI;

import javax.mail.MessagingException;
import javax.naming.NamingException;

public class UITesting {
    public static void main(String[] args) throws MessagingException, NamingException {
        System.out.println(Account.addUser("18xxperson@gmail.com","Rockband3","Jonathan",
                null,null,null,null,null,null));
        System.out.print(Account.forgotPassword("18xxperson@gmail.com"));
    }
}
