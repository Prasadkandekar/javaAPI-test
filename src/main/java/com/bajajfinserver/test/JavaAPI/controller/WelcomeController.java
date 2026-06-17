package com.bajajfinserver.test.JavaAPI.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@Slf4j
public class WelcomeController {
    
    @GetMapping
    public ResponseEntity<Map<String, String>> welcome() {
        log.info("Received request to root endpoint");
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to Java Finserve Health API Test");
        response.put("version", "1.0.0");
        response.put("status", "running");
        response.put("endpoints", "GET /bfhl, POST /bfhl, GET /health");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        log.info("Received request to health endpoint");
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "JavaAPI");
        response.put("timestamp", System.currentTimeMillis());
        response.put("message", "Service is healthy and running");
        
        return ResponseEntity.ok(response);
    }
}
