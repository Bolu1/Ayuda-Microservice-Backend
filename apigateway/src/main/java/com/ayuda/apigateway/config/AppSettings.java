package com.ayuda.apigateway.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppSettings {
    // JWT Secrets
    @Value("${jwt.secret}")
    private String jwtSecret;
}
