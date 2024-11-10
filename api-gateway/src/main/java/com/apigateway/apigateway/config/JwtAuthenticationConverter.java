package com.apigateway.apigateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

    private final GateWayJwtService gateWayJwtService;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        log.info(exchange.getRequest().getHeaders().getFirst(org.springframework.http.HttpHeaders.AUTHORIZATION));
        log.info("Request method is {}", exchange.getRequest().getMethod());
        log.info("All headers is {}", exchange.getRequest().getHeaders());
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(header ->{
                    boolean hasBearer = header.startsWith("Bearer ");
                    log.info("has Bearer token {}", hasBearer);
                    return hasBearer;
                })
                .map(header -> header.substring(7))
                .flatMap(token -> {
                    try {
                        log.info("This is the token for khela , {}", token);
                        String userName = gateWayJwtService.extractUserName(token);
                        log.info("User Name is {}", userName);
                        List<String> roles = gateWayJwtService.extractUserRoles(token);
                        log.info("Roles are here bro bro bro : {}", roles);
                        List<SimpleGrantedAuthority> authorities = roles
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList();
                        log.info("granted authorities {}", authorities);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userName,
                                null,
                                authorities
                        );
                        log.info("Created authentication token {}", usernamePasswordAuthenticationToken);
                        log.info("Auth token authorities {}", usernamePasswordAuthenticationToken.getAuthorities());

                        return Mono.just((Authentication)usernamePasswordAuthenticationToken);

                    } catch (Exception e) {
                        log.error("JWT Processing Error", e);
                        return Mono.empty();
                    }
                })
                .filter(Objects::nonNull);
    }
}
