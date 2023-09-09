package com.memesKenya.meme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//discards Spring security's auto-configuration
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@SpringBootApplication
@EnableJpaRepositories

public class MemeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemeApplication.class, args);
	}
}
