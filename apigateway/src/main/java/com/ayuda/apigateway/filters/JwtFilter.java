package com.ayuda.apigateway.filters;

import com.ayuda.apigateway.utils.security.JwtService;
import com.ayuda.apigateway.validator.RouterValidator;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class JwtFilter implements GatewayFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_ID_HEADER = "X-User-Id";

    private final RouterValidator routerValidator;
    private final JwtService jwtService;
    private final FilterUtility filterUtility;

    @Autowired
    public JwtFilter(RouterValidator routerValidator, JwtService jwtService, FilterUtility filterUtility) {
        this.routerValidator = routerValidator;
        this.jwtService = jwtService;
        this.filterUtility = filterUtility;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request)) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String token = this.extractToken(request);

            if (!jwtService.isTokenValid(token)) {
                return this.onError(exchange, HttpStatus.FORBIDDEN);
            }

            // ✅ Extract user ID from JWT
            Claims claims = jwtService.extractClaim(token);
            String userId = claims.getSubject();
            System.out.println("✅ Extracted User ID: " + userId);

            // ✅ Ensure request is mutated properly like Correlation ID
            exchange = filterUtility.setRequestHeader(exchange, USER_ID_HEADER, userId);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String extractToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length()).trim();
        }
        return null;
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(AUTHORIZATION_HEADER);
    }
}
