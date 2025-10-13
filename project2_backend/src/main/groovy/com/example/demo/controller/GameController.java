package com.example.demo.controller;

import com.example.demo.model.Game;
import java.util.List;
import com.example.demo.service.GameService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameController {
    
    @Autowired
    private GameService gameService;

    @GetMapping("/test")
    public String testEndpoint() {
        return "API is working! GameController is responding.";
    }

    @GetMapping("/all")
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/team/{teamId}")
    public List<Game> getGamesByTeam(@PathVariable Long teamId){
        try {
            return gameService.getGamesByTeam(teamId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching games for team " + teamId + ": " + e.getMessage());
        }
    }
}
