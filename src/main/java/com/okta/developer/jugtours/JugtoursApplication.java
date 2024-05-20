package com.okta.developer.jugtours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JugtoursApplication {

	public static void main(String[] args) {
		SpringApplication.run(JugtoursApplication.class, args);
	}

}

//(scanBasePackages={
//		"com.okta.developer.jugtours.config","com.okta.developer.jugtours.model", "com.okta.developer.jugtours.web" })