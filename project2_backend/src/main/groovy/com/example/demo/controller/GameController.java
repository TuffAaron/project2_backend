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

    @GetMapping("/")
    public String getApiRoutes() {
        return "<h1>Game API Routes</h1>" +
               "<ul>" +
               "<li><a href='/api/games/test'>Test Endpoint</a> - Check if API is working</li>" +
               "<li><a href='/api/games/all'>All Games</a> - Get all games</li>" +
               "<li><a href='/api/games/team/{teamId}'>Games by Team</a> - Get games for a specific team (replace {teamId} with actual team ID)</li>" +
               "</ul>" +
               "<p>Example: <a href='/api/games/team/1'>Games for Team 1</a></p>";
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "API is working! GameController is responding.";
    }

    @GetMapping("/all")
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/team/{teamId}")
    public List<Game> getGamesByTeam(@PathVariable("teamId") Long teamId){
        try {
            return gameService.getGamesByTeam(teamId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching games for team " + teamId + ": " + e.getMessage());
        }
    }
}
