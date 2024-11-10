package com.apigateway.apigateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Slf4j
public class GateWaySecurityConfig {

    private final GateWayJwtService gateWayJwtService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(e -> e
                        .pathMatchers("/api/v1/auth/**", "/api/v1/home/**").permitAll()
                        .pathMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .pathMatchers("/api/v1/users/**").hasAnyRole("USER", "ADMIN")
                        .pathMatchers("/api/v1/dispatcher/**").hasAnyRole("ADMIN", "DISPATCHER")
                        .pathMatchers("/api/v1/packager/**").hasAnyRole("ADMIN", "PACKAGER")
                        .pathMatchers("/api/v1/stockmanager/**").hasAnyRole("ADMIN", "STOCKMANAGER")
                        .pathMatchers("/api/v1/storemanager/**").hasAnyRole("ADMIN", "STOREMANAGER")
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationWebFilter jwtAuthenticationWebFilter() {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager());
        authenticationWebFilter.setServerAuthenticationConverter(new JwtAuthenticationConverter(gateWayJwtService));
        return authenticationWebFilter;
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return new JwtAuthenticationManager();
    }

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

//    @Bean
//    public GlobalFilter forwardAuthFilter() {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//            if (authHeader != null) {
//                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//                        .header("X-Forwarded-Authorization", authHeader)
//                        .build();
//
//                return chain.filter(exchange.mutate().request(modifiedRequest).build());
//            }
//            return chain.filter(exchange);
//        };
//    }

}
