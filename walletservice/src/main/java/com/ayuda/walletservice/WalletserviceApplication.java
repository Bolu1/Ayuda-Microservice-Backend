package com.ayuda.walletservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Wallet microservice REST API Documentation",
				description = "Ayuda wallet microservice REST API Documentation",
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
				description =  "Ayuda wallet microservice REST API Documentation"
		)
)
public class WalletserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletserviceApplication.class, args);
	}

}
