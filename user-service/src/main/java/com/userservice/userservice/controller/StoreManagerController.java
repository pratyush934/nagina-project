package com.userservice.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/storemanager")
public class StoreManagerController {

    @GetMapping("/hello")
    public String helloStoreManager() {
        return "Hello StoreManager";
    }
}
