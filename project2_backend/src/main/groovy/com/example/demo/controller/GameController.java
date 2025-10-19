package com.example.demo.controller;

import com.example.demo.model.Game;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.example.demo.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    public Map<String, Object> testEndpoint(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Games API is working! User is authenticated.");
        response.put("timestamp", System.currentTimeMillis());
        
        if (principal != null) {
            response.put("authenticatedUser", principal.getAttribute("name") != null ? 
                principal.getAttribute("name") : principal.getAttribute("login"));
            response.put("provider", getProvider(principal));
        }
        
        return response;
    }

    @GetMapping("/all")
    public Map<String, Object> getAllGames(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Game> games = gameService.getAllGames();
            response.put("status", "success");
            response.put("games", games);
            response.put("count", games.size());
            response.put("requestedBy", principal != null ? 
                (principal.getAttribute("name") != null ? principal.getAttribute("name") : principal.getAttribute("login")) 
                : "anonymous");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error fetching games: " + e.getMessage());
        }
        
        return response;
    }

    @GetMapping("/team/{teamId}")
    public Map<String, Object> getGamesByTeam(@PathVariable Long teamId, @AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Game> games = gameService.getGamesByTeam(teamId);
            response.put("status", "success");
            response.put("teamId", teamId);
            response.put("games", games);
            response.put("count", games.size());
            response.put("requestedBy", principal != null ? 
                (principal.getAttribute("name") != null ? principal.getAttribute("name") : principal.getAttribute("login")) 
                : "anonymous");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error fetching games for team " + teamId + ": " + e.getMessage());
            response.put("teamId", teamId);
        }
        
        return response;
    }

    @GetMapping("/{gameId}")
    public Map<String, Object> getGameById(@PathVariable String gameId, @AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Game game = gameService.getGameById(gameId);
            if (game != null) {
                response.put("status", "success");
                response.put("game", game);
                response.put("requestedBy", principal != null ? 
                    (principal.getAttribute("name") != null ? principal.getAttribute("name") : principal.getAttribute("login")) 
                    : "anonymous");
            } else {
                response.put("status", "not_found");
                response.put("message", "Game with ID " + gameId + " not found");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error fetching game " + gameId + ": " + e.getMessage());
        }
        
        return response;
    }

    private String getProvider(OAuth2User principal) {
        if (principal.getAttribute("login") != null) return "GitHub";
        if (principal.getAttribute("picture") != null) return "Google";
        if (principal.getAttribute("id") != null && principal.getAttribute("username") != null) return "Discord";
        return "Unknown";
    }
}
