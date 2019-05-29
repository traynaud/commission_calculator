package com.malt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Entry Point
 * 
 * @author Tanguy
 * @version 1.0
 * @since 29 May 2019
 *
 */
@EnableJpaRepositories(basePackages = "com.malt.repositories")
@SpringBootApplication
@EnableAsync
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
