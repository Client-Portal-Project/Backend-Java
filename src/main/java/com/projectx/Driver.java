package com.projectx;

import com.projectx.UI.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;

@SpringBootApplication
public class Driver {

	public static final String CROSS_ORIGIN_VALUE = "http://localhost:4200";

	public static void main(String[] args) throws MessagingException {
		SpringApplication.run(Driver.class, args);
		Account account=new Account();
		System.out.println(account.addUser("18xxperson@gmail.com","Rockband","Jonathan"));
		account.verifyEmail("18xxperson@gmail.com");
		System.out.println(account.forgotPassword("18xxperson@gmail.com"));
		account.UpdatePassword("Rockband3","18xxperson@gmail.com");
	}
}
