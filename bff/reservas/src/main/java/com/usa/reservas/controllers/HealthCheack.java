package com.usa.reservas.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class HealthCheack {
    
    @GetMapping("/health")
    public String health() {
        return "v_1.0.0";
    }

}
