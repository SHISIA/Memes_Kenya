package com.memesKenya.meme;

import com.memesKenya.meme.configs.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//discards Spring security's auto-configuration
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties(RsaKeyProperties.class)

public class MemeApplication {
	public static void main(String[] args) {
		SpringApplication.run(MemeApplication.class, args);
	}
}
