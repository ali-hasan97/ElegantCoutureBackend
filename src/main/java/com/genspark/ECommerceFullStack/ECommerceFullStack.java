package com.genspark.ECommerceFullStack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class ECommerceFullStack {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceFullStack.class, args);
	}

}
