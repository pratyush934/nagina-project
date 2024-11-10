package com.apigateway.apigateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

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
                        .pathMatchers("/api/v1/users/**")
                        .access((authentication, context) -> {
                            log.info("Checking access for /api/v1/users/** path");
                            log.info("Authentication: {}", authentication);
                            log.info("Context: {}", context);
                            return authentication
                                    .map(auth -> {
                                        log.info("Auth authorities: {}", auth.getAuthorities());
                                        boolean hasAccess = auth.getAuthorities().stream()
                                                .anyMatch(a -> a.getAuthority().equals("ROLE_USER") ||
                                                        a.getAuthority().equals("ROLE_ADMIN"));
                                        log.info("Has access: {}", hasAccess);
                                        return hasAccess;
                                    })
                                    .map(AuthorizationDecision::new);
                        })
                        .pathMatchers("/api/v1/admin/**").hasAuthority("ROLE_ADMIN")
                        .pathMatchers("/api/v1/dispatcher/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_DISPATCHER")
                        .pathMatchers("/api/v1/packager/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_PACKAGER")
                        .pathMatchers("/api/v1/stockmanager/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STOCKMANAGER")
                        .pathMatchers("/api/v1/storemanager/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STOREMANAGER")
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


}
