package com.userservice.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @GetMapping("/hello")
    public String goodGoing() {
        log.info("I am Bond");
        log.info("Bond James Bond");
        return "I am User and Who u are";
    }
}
