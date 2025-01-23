package com.ayuda.apigateway.config;

import com.ayuda.apigateway.filters.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Configuration
@EnableHystrix
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // ✅ USER SERVICE (Requires JWT)
                .route("user-service", r -> r
                        .path("/userservice/**")
                        .filters(f -> f
                                .filter(jwtFilter) // 🔒 Apply JWT Authentication
                                .rewritePath("/userservice/(?<segment>.*)", "/${segment}") // ✅ Rewrite Path
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("ayudaCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport"))
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                        )
                        .uri("lb://USERSERVICE"))

                // ✅ AUTHENTICATION SERVICE (Public routes, No JWT needed)
                .route("authentication-service", r -> r
                        .path("/authenticationservice/**")
                        .filters(f -> f
                                .rewritePath("/authenticationservice/(?<segment>.*)", "/${segment}") // ✅ Rewrite Path
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver()))
                        )
                        .uri("lb://AUTHENTICATIONSERVICE"))
                .build();
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 1, 1);
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .defaultIfEmpty("anonymous");
    }
}
