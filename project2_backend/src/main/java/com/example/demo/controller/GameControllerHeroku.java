package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import java.util.*;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
@Profile("heroku")
public class GameControllerHeroku {

    @GetMapping("/test")
    public String testEndpoint() {
        return "API is working! GameController is responding (Database disabled).";
    }

    @GetMapping("/all")
    public Map<String, Object> getAllGames() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Database temporarily disabled due to JawsDB quota limits");
        response.put("games", new ArrayList<>());
        response.put("status", "database_disabled");
        return response;
    }

    @GetMapping("/team/{teamId}")
    public Map<String, Object> getGamesByTeam(@PathVariable Long teamId) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Database temporarily disabled due to JawsDB quota limits");
        response.put("teamId", teamId);
        response.put("games", new ArrayList<>());
        response.put("status", "database_disabled");
        return response;
    }

    @GetMapping("/")
    public String home() {
        return "Jump Ball API is running! Database temporarily disabled due to quota limits.";
    }
}
