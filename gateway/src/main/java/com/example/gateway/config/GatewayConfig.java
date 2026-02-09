package com.example.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("account-service", r -> r.path("/account/**", "/creditCard/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://account-service"))
                .route("transaction-service", r -> r.path("/transaction/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://transaction-service"))
                .route("get-service", r -> r.path("/test")
                        .filters(f -> f.filter(filter))
                        .uri("lb://get-service"))

                .build();
    }

}