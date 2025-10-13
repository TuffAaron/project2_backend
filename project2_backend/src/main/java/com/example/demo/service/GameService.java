package com.example.demo.service;

import com.example.demo.model.Game;
import com.example.demo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;

    public List<Game> getGamesByTeam(Long teamId) {
        // Find games where the team is either home or away team
        return gameRepository.findByHomeTeamIdOrAwayTeamId(teamId, teamId);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
}