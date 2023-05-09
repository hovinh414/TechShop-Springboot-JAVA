package com.shoptech.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages={"entity", "com.shoptech.admin.user"})
public class ShoptechBackEndApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(ShoptechBackEndApplication.class, args);
	}

}
