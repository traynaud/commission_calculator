package com.malt;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Enable Swagger-UI
 *
 * @author Tanguy
 * @version 1.0
 * @since 02 June 2019
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String API_VERSION = "1.0 Stable";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.malt.controllers")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo()).useDefaultResponseMessages(false);

	}

	private ApiInfo apiInfo() {
		final ApiInfo apiInfo = new ApiInfo(
				"Malt Commission Calculator", "Compute the commission of freelancers based on generic parameters",
				API_VERSION, "All rights reserved", new Contact("T.Raynaud",
						"https://github.com/traynaud/commission_calculator", "tanguy.raynaud.@gmail.com"),
				"License of API", "API license URL", Collections.emptyList());
		return apiInfo;
	}

}
