package com.ayuda.user;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

//@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
//@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "User microservice REST API Documentation",
				description = "Ayuda user microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Adetifa Bolu",
						email = "boluadetifa@gmail.com",
						url = "https://www.boluwatife.dev"
				),
				license = @License(
						name = "Apache 2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "Ayuda user microservice REST API Documentation"
		)
)
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
