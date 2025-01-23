//package com.ayuda.apigateway.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
//
//@Configuration
//@EnableWebFluxSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF for JWT
//                .authorizeExchange(exchange -> exchange
//                        .pathMatchers(HttpMethod.POST, "/authenticationservice/api/v1/auth/signin").permitAll() // Public auth endpoints
//                        .pathMatchers(HttpMethod.GET, "/actuator/**").permitAll() // Monitoring
//                        .anyExchange().authenticated() // Secure all other endpoints
//                )
//                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))
//                .build();
//    }
//}
