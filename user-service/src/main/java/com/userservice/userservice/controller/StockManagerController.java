package com.userservice.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stockmanager")
public class StockManagerController {

    @GetMapping("/hello")
    public String helloStockManager() {
        return "Hello StockManager";
    }
}
