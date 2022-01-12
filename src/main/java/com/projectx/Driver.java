package com.projectx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Driver {

	public static final String CROSS_ORIGIN_VALUE = "http://localhost:4200";

	public static void main(String[] args) {
		SpringApplication.run(Driver.class, args);
	}
}
