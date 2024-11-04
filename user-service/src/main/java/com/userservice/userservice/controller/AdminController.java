package com.userservice.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@Slf4j
public class AdminController {


    @GetMapping("/home")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String helloAdmin(Authentication authentication) {
        log.info("I am inside the helloAdmin function");
        log.info("I am getting authentication authorities {}", authentication.getAuthorities());
        log.info("I am looking at something imp like is authenticated {}", authentication.isAuthenticated());
        log.info("Khadus principal is {}", authentication.getPrincipal());
        return "Hello Admin";
    }
}
