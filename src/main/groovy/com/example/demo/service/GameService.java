package com.example.demo.service;

import com.example.demo.model.Game;
import com.example.demo.model.Team;
import com.example.demo.repository.GameRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.dto.GameDTO;
import com.example.demo.dto.GameDTO.TeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<GameDTO> getGamesByTeamWithDetails(Long teamId){
        List<Game> games = gameRepository.findByHomeTeamIdOrAwayTeamId(teamId, teamId);
        return games.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public GameDTO getGameByIdWithDetails(String gameId){
        Game game = gameRepository.findById(gameId).orElse(null);
        return game != null ? convertToDTO(game) : null;
    }

    private GameDTO convertToDTO(Game game){
        Team homeTeam = teamRepository.findById(game.getHomeTeamId())
            .orElse(createDefaultTeam(game.getHomeTeamId()));
        
        Team awayTeam = teamRepository.findById(game.getAwayTeamId())
            .orElse(createDefaultTeam(game.getAwayTeamId()));
        
        TeamInfo homeTeamInfo = new TeamInfo(
            homeTeam.getTeamId(),
            homeTeam.getTeamName(),
            homeTeam.getTeamTricode(),
            homeTeam.getLogo(),
            game.getHomeTeamScore()
        );
        
        TeamInfo awayTeamInfo = new TeamInfo(
            awayTeam.getTeamId(),
            awayTeam.getTeamName(),
            awayTeam.getTeamTricode(),
            awayTeam.getLogo(),
            game.getAwayTeamScore()
        );
        
        return new GameDTO(
            game.getGameId(),
            game.getGameDateTimeEst(),
            game.getGameStatusText(),
            homeTeamInfo,
            awayTeamInfo
        );
    }

    private Team createDefaultTeam(Long teamId) {
        Team team = new Team();
        team.setTeamId(teamId);
        team.setTeamName("Unknown Team");
        team.setTeamTricode("???");
        team.setLogo("");
        return team;
    }

    public List<Game> getGamesByTeam(Long teamId) {
        // Find games where the team is either home or away team
        return gameRepository.findByHomeTeamIdOrAwayTeamId(teamId, teamId);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
    
    public Game getGameById(String gameId) {
        return gameRepository.findById(gameId).orElse(null);
    }

    // CREATE - Add a new game
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    // UPDATE - Replace an entire game resource
    public Game updateGame(String gameId, Game gameDetails) {
        Optional<Game> existingGame = gameRepository.findById(gameId);
        if (existingGame.isPresent()) {
            gameDetails.setGameId(gameId); // Ensure the ID matches
            return gameRepository.save(gameDetails);
        }
        return null;
    }

    // PATCH - Partially update a game
    public Game patchGame(String gameId, Game gameUpdates) {
        Optional<Game> existingGameOpt = gameRepository.findById(gameId);
        if (existingGameOpt.isPresent()) {
            Game existingGame = existingGameOpt.get();

            // Update only non-null fields
            if (gameUpdates.getGameCode() != null) existingGame.setGameCode(gameUpdates.getGameCode());
            if (gameUpdates.getGameStatus() != null) existingGame.setGameStatus(gameUpdates.getGameStatus());
            if (gameUpdates.getGameStatusText() != null) existingGame.setGameStatusText(gameUpdates.getGameStatusText());
            if (gameUpdates.getGameSequence() != null) existingGame.setGameSequence(gameUpdates.getGameSequence());
            if (gameUpdates.getGameDateEst() != null) existingGame.setGameDateEst(gameUpdates.getGameDateEst());
            if (gameUpdates.getGameTimeEst() != null) existingGame.setGameTimeEst(gameUpdates.getGameTimeEst());
            if (gameUpdates.getGameDateTimeEst() != null) existingGame.setGameDateTimeEst(gameUpdates.getGameDateTimeEst());
            if (gameUpdates.getGameDateUTC() != null) existingGame.setGameDateUTC(gameUpdates.getGameDateUTC());
            if (gameUpdates.getGameTimeUTC() != null) existingGame.setGameTimeUTC(gameUpdates.getGameTimeUTC());
            if (gameUpdates.getGameDateTimeUTC() != null) existingGame.setGameDateTimeUTC(gameUpdates.getGameDateTimeUTC());
            if (gameUpdates.getAwayTeamTime() != null) existingGame.setAwayTeamTime(gameUpdates.getAwayTeamTime());
            if (gameUpdates.getHomeTeamTime() != null) existingGame.setHomeTeamTime(gameUpdates.getHomeTeamTime());
            if (gameUpdates.getDay() != null) existingGame.setDay(gameUpdates.getDay());
            if (gameUpdates.getMonthNum() != null) existingGame.setMonthNum(gameUpdates.getMonthNum());
            if (gameUpdates.getWeekNumber() != null) existingGame.setWeekNumber(gameUpdates.getWeekNumber());
            if (gameUpdates.getWeekName() != null) existingGame.setWeekName(gameUpdates.getWeekName());
            if (gameUpdates.getIfNecessary() != null) existingGame.setIfNecessary(gameUpdates.getIfNecessary());
            if (gameUpdates.getSeriesGameNumber() != null) existingGame.setSeriesGameNumber(gameUpdates.getSeriesGameNumber());
            if (gameUpdates.getGameLabel() != null) existingGame.setGameLabel(gameUpdates.getGameLabel());
            if (gameUpdates.getGameSubLabel() != null) existingGame.setGameSubLabel(gameUpdates.getGameSubLabel());
            if (gameUpdates.getSeriesText() != null) existingGame.setSeriesText(gameUpdates.getSeriesText());
            if (gameUpdates.getArenaName() != null) existingGame.setArenaName(gameUpdates.getArenaName());
            if (gameUpdates.getArenaState() != null) existingGame.setArenaState(gameUpdates.getArenaState());
            if (gameUpdates.getArenaCity() != null) existingGame.setArenaCity(gameUpdates.getArenaCity());
            if (gameUpdates.getPostponedStatus() != null) existingGame.setPostponedStatus(gameUpdates.getPostponedStatus());
            if (gameUpdates.getBranchLink() != null) existingGame.setBranchLink(gameUpdates.getBranchLink());
            if (gameUpdates.getGameSubtype() != null) existingGame.setGameSubtype(gameUpdates.getGameSubtype());
            if (gameUpdates.getIsNeutral() != null) existingGame.setIsNeutral(gameUpdates.getIsNeutral());
            if (gameUpdates.getHomeTeamId() != null) existingGame.setHomeTeamId(gameUpdates.getHomeTeamId());
            if (gameUpdates.getAwayTeamId() != null) existingGame.setAwayTeamId(gameUpdates.getAwayTeamId());
            if (gameUpdates.getHomeTeamScore() != null) existingGame.setHomeTeamScore(gameUpdates.getHomeTeamScore());
            if (gameUpdates.getAwayTeamScore() != null) existingGame.setAwayTeamScore(gameUpdates.getAwayTeamScore());

            return gameRepository.save(existingGame);
        }
        return null;
    }

    // DELETE - Remove a game
    public boolean deleteGame(String gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            gameRepository.deleteById(gameId);
            return true;
        }
        return false;
    }

    // Check if game exists
    public boolean gameExists(String gameId) {
        return gameRepository.existsById(gameId);
    }
}
