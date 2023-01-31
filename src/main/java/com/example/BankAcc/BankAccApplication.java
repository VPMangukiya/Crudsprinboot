package com.example.BankAcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@SpringBootApplication
public class BankAccApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAccApplication.class, args);
	}

}
