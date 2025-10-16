package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/status")
@CrossOrigin(origins = "*")
public class StatusController {

    @GetMapping("/health")
    public Map<String, Object> healthCheck() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("message", "Application is running");
        status.put("timestamp", new Date());
        status.put("database", "DISABLED - JawsDB quota exhausted");
        return status;
    }

    @GetMapping("/")
    public String home() {
        return "Jump Ball API is running! Database temporarily disabled due to quota limits.";
    }
}
