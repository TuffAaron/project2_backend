package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Game {
    
    @Id
    @Column(name = "game_id")
    private String gameId;

    @Column(name = "game_date")
    private LocalDateTime gameDate;

    @Column(name = "home_team_id")
    private Long homeTeamId;

    @Column(name = "away_team_id")
    private Long awayTeamId;

    @Column(name = "game_status")
    private String gameStatus;

    public Game(){
    }

    public Game(String gameId, LocalDateTime gameDate, Long homeTeamId, Long awayTeamId, String gameStatus){
        this.gameId = gameId;
        this.gameDate = gameDate;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.gameStatus = gameStatus;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDateTime gameDate) {
        this.gameDate = gameDate;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Long awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public String toString() {
        return "Game [gameId=" + gameId + ", gameDate=" + gameDate + ", homeTeamId=" + homeTeamId + ", awayTeamId="
                + awayTeamId + ", gameStatus=" + gameStatus + "]";
    }
}