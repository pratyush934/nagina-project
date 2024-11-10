package com.storeservice.storeservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/storemanager")
public class FirstController {

    @GetMapping("/hello")
    public String hello() {
        return "hello StoreManager";
    }
}
