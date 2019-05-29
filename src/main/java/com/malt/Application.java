package com.malt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaRepositories(basePackages = "com.malt.repositories")
@SpringBootApplication
@EnableAsync
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
