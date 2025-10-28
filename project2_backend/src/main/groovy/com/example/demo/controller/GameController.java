package com.example.demo.controller;

import com.example.demo.model.Game;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.example.demo.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

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
               "<li>POST /api/games - Create a new game</li>" +
               "<li>PUT /api/games/{gameId} - Update an entire game</li>" +
               "<li>PATCH /api/games/{gameId} - Partially update a game</li>" +
               "<li>DELETE /api/games/{gameId} - Delete a game</li>" +
               "</ul>" +
               "<p>Example: <a href='/api/games/team/1'>Games for Team 1</a></p>";
    }

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
    public Map<String, Object> getGamesByTeam(@PathVariable ("teamId") Long teamId, @AuthenticationPrincipal OAuth2User principal) {
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
    public ResponseEntity<Map<String, Object>> getGameById(@PathVariable("gameId") String gameId, @AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Game game = gameService.getGameById(gameId);
            if (game != null) {
                response.put("status", "success");
                response.put("game", game);
                response.put("requestedBy", principal != null ? 
                    (principal.getAttribute("name") != null ? principal.getAttribute("name") : principal.getAttribute("login")) 
                    : "anonymous");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "not_found");
                response.put("message", "Game with ID " + gameId + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error fetching game " + gameId + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST - Create a new game
    @PostMapping
    public ResponseEntity<Map<String, Object>> createGame(@RequestBody Game game, @AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Check if game with this ID already exists
            if (game.getGameId() != null && gameService.gameExists(game.getGameId())) {
                response.put("status", "conflict");
                response.put("message", "Game with ID " + game.getGameId() + " already exists. Use PUT to update.");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            Game createdGame = gameService.createGame(game);
            response.put("status", "created");
            response.put("message", "Game created successfully");
            response.put("game", createdGame);
            response.put("createdBy", principal != null ?
                (principal.getAttribute("name") != null ? principal.getAttribute("name") : principal.getAttribute("login"))
                : "anonymous");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error creating game: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PUT - Update entire game (replace)
    @PutMapping("/{gameId}")
    public ResponseEntity<Map<String, Object>> updateGame(@PathVariable("gameId") String gameId, @RequestBody Game game, @AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();

        try {
            Game updatedGame = gameService.updateGame(gameId, game);

            if (updatedGame != null) {
                response.put("status", "success");
                response.put("message", "Game updated successfully");
                response.put("game", updatedGame);
                response.put("updatedBy", principal != null ?
                    (principal.getAttribute("name") != null ? principal.getAttribute("name") : principal.getAttribute("login"))
                    : "anonymous");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "not_found");
                response.put("message", "Game with ID " + gameId + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error updating game: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH - Partially update game
    @PatchMapping("/{gameId}")
    public ResponseEntity<Map<String, Object>> patchGame(@PathVariable("gameId") String gameId, @RequestBody Game gameUpdates, @AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();

        try {
            Game patchedGame = gameService.patchGame(gameId, gameUpdates);

            if (patchedGame != null) {
                response.put("status", "success");
                response.put("message", "Game partially updated successfully");
                response.put("game", patchedGame);
                response.put("updatedBy", principal != null ?
                    (principal.getAttribute("name") != null ? principal.getAttribute("name") : principal.getAttribute("login"))
                    : "anonymous");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "not_found");
                response.put("message", "Game with ID " + gameId + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error patching game: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE - Delete a game
    @DeleteMapping("/{gameId}")
    public ResponseEntity<Map<String, Object>> deleteGame(@PathVariable("gameId") String gameId, @AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean deleted = gameService.deleteGame(gameId);

            if (deleted) {
                response.put("status", "success");
                response.put("message", "Game with ID " + gameId + " deleted successfully");
                response.put("deletedBy", principal != null ?
                    (principal.getAttribute("name") != null ? principal.getAttribute("name") : principal.getAttribute("login"))
                    : "anonymous");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "not_found");
                response.put("message", "Game with ID " + gameId + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error deleting game: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private String getProvider(OAuth2User principal) {
        if (principal.getAttribute("login") != null) return "GitHub";
        if (principal.getAttribute("picture") != null) return "Google";
        if (principal.getAttribute("id") != null && principal.getAttribute("username") != null) return "Discord";
        return "Unknown";
    }
}
