package com.apigateway.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class GateWayRoutingConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("user-service-auth", r -> r
                        .path("/api/v1/auth/**", "/api/v1/home/**")
                        .uri("lb://USER-SERVICE"))
                .route("user-service", r -> r
                        .path("/api/v1/users/**")
                        .uri("lb://USER-SERVICE"))
                .route("dispatcher-service", r -> r
                        .path("/api/v1/dispatcher/**")
                        .uri("lb://DISPATCHER-SERVICE"))
                .route("packager-service", r -> r
                        .path("/api/v1/packager/**")
                        .uri("lb://PACKAGER-SERVICE"))
                .route("store-manager-service", r -> r
                        .path("/api/v1/storemanager/**")
                        .uri("lb://STORE-SERVICE"))
                .route("stock-manager-service", r -> r
                        .path("/api/v1/stockmanager/**")
                        .uri("lb://STOCK-SERVICE"))
                .build();
    }
//
//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//
//        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Access-Control-Allow-Origin", "X-Forwarded-Authorization"));
//        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
//        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
//        corsConfiguration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//
//
//        // No casting needed here
//        return new CorsWebFilter((CorsConfigurationSource) urlBasedCorsConfigurationSource);
//    }

}
