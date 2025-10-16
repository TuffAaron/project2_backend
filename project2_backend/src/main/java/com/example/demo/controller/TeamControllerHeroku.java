package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.Profile;
import java.util.*;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "*")
@Profile("heroku")
public class TeamControllerHeroku {

    @GetMapping("/all")
    public Map<String, Object> getAllTeams() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Database temporarily disabled due to JawsDB quota limits");
        response.put("teams", new ArrayList<>());
        response.put("status", "database_disabled");
        return response;
    }

    @GetMapping("/")
    public String home() {
        return "Teams API is running! Database temporarily disabled due to quota limits.";
    }
}
