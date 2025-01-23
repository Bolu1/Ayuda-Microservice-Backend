package com.ayuda.apigateway;

import com.ayuda.apigateway.filters.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApigatewayApplication {

	private final JwtFilter jwtFilter;

	public ApigatewayApplication(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}
}
