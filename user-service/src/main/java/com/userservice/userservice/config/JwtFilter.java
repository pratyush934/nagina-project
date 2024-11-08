package com.userservice.userservice.config;

import com.userservice.userservice.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = authHeader.substring(7);
            final String userName = jwtService.extractUserName(token);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            logger.debug("Request uri is {} and Authentication holds {}", request.getRequestURI(), authentication);

            if (userName != null && authentication == null) {
                UserDetails userDetails = userRepository.findByEmail(userName)
                        .orElseThrow(() -> new NoSuchElementException("Not found it is from JwtFilter"));

                if (jwtService.isUserVerified(token, userDetails)) {
                    List<String> roles = jwtService.extractRoles(token);

                    List<SimpleGrantedAuthority> grantedAuthorities = roles
                            .stream()
                            .map(role -> new SimpleGrantedAuthority(role))
                            .toList();

                    logger.debug("Extracted userName is {}", userName);
                    logger.debug("Roles we get wrt to user is {}", roles);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            grantedAuthorities
                    );

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    logger.debug("We have set securityContextHolder have you seen it , it is in JwtFilter {}", SecurityContextHolder.getContext().getAuthentication());
                }
            } else {
                logger.warn("The userName is some how quite suspicious and please look at JwtFilter {}", userName);
            }

        } catch (Exception e) {
            logger.warn("The error message is in the file JwtFilter Class {}", e.getMessage());
//            handlerExceptionResolver.resolveException(request, response, null, e);
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
