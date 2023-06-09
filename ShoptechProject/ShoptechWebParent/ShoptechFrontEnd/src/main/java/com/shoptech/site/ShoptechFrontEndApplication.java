package com.shoptech.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShoptechFrontEndApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/tech");
		SpringApplication.run(ShoptechFrontEndApplication.class, args);
	}

}
