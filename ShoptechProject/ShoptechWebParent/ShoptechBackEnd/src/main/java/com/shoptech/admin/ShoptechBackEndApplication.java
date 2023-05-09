package com.shoptech.admin;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages={"com.shoptech.site.entity", "com.shoptech.admin.user"})
public class ShoptechBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoptechBackEndApplication.class, args);
	}

}
