package com.packagerservice.packagerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/packager")
public class FirstController {

    @GetMapping("/hello")
    public String hello() {
        return "hello-packager";
    }
}
