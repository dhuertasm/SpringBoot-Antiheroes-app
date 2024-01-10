package com.dahuertasm.springbootsuperheroes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class SpringBootSuperheroesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSuperheroesApplication.class, args);
	}

}
