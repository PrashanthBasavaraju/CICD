package com.triconinfotech.WealthWise;

import java.time.LocalDate;



import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import com.triconinfotech.WealthWise.security.ErrorConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfig.
 */
@Configuration
@EnableSwagger2

public class SwaggerConfig {

	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return List.of(new SecurityReference("JWT", authorizationScopes));
	}

	@Bean
	public Docket customImplementation() {

		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.triconinfotech.WealthWise")).build()
				.securitySchemes(List.of(apiKey())).securityContexts(Collections.singletonList(securityContext()))
				.apiInfo(apiInfo()).pathMapping("/").useDefaultResponseMessages(false)
				.directModelSubstitute(LocalDate.class, String.class).genericModelSubstitutes(ResponseEntity.class);
	}

	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("WealthWise Service").version("1.0.0").description("Wealthwise Backend API")
				.contact(new Contact(ErrorConstants.DEV_NAME, ErrorConstants.DEV_CONTACT, ErrorConstants.DEV_EMAIL))
				.build();
	}

}
