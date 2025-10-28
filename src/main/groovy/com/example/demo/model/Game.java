package com.example.demo.model;

import java.time.LocalDateTime;

import java.time.ZonedDateTime;

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

    @Column(name = "game_code")
    private String gameCode;

    @Column(name = "game_status")
    private Integer gameStatus;

    @Column(name = "game_status_text")
    private String gameStatusText;

    @Column(name = "game_sequence")
    private Integer gameSequence;

    @Column(name = "game_date_est")
    private ZonedDateTime gameDateEst;

    @Column(name = "game_time_est")
    private ZonedDateTime gameTimeEst;

    @Column(name = "game_date_time_est")
    private ZonedDateTime gameDateTimeEst;

    @Column(name = "game_date_utc")
    private ZonedDateTime gameDateUTC;

    @Column(name = "game_time_utc")
    private ZonedDateTime gameTimeUTC;

    @Column(name = "game_date_time_utc")
    private ZonedDateTime gameDateTimeUTC;

    @Column(name = "away_team_time")
    private ZonedDateTime awayTeamTime;

    @Column(name = "home_team_time")
    private ZonedDateTime homeTeamTime;

    @Column(name = "game_day")
    private String day;

    @Column(name = "month_num")
    private Integer monthNum;

    @Column(name = "week_number")
    private Integer weekNumber;

    @Column(name = "week_name")
    private String weekName;

    @Column(name = "if_necessary")
    private String ifNecessary;

    @Column(name = "series_game_number")
    private String seriesGameNumber;

    @Column(name = "game_label")
    private String gameLabel;

    @Column(name = "game_sub_label")
    private String gameSubLabel;

    @Column(name = "series_text")
    private String seriesText;

    @Column(name = "arena_name")
    private String arenaName;

    @Column(name = "arena_state")
    private String arenaState;

    @Column(name = "arena_city")
    private String arenaCity;

    @Column(name = "postponed_status")
    private String postponedStatus;

    @Column(name = "branch_link")
    private String branchLink;

    @Column(name = "game_subtype")
    private String gameSubtype;

    @Column(name = "is_neutral")
    private Boolean isNeutral;

    @Column(name = "home_team_id")
    private Long homeTeamId;

    @Column(name = "away_team_id")
    private Long awayTeamId;

    @Column(name = "home_team_score")
    private Integer homeTeamScore;

    @Column(name = "away_team_score")
    private Integer awayTeamScore;

    // Constructors
    public Game(){
    }

    public Game(String gameId, String gameCode, Integer gameStatus, String gameStatusText){
        this.gameId = gameId;
        this.gameCode = gameCode;
        this.gameStatus = gameStatus;
        this.gameStatusText = gameStatusText;
    }

    // Getters and Setters
    public String getGameId() { return gameId; }
    public void setGameId(String gameId) { this.gameId = gameId; }

    public String getGameCode() { return gameCode; }
    public void setGameCode(String gameCode) { this.gameCode = gameCode; }

    public Integer getGameStatus() { return gameStatus; }
    public void setGameStatus(Integer gameStatus) { this.gameStatus = gameStatus; }

    public String getGameStatusText() { return gameStatusText; }
    public void setGameStatusText(String gameStatusText) { this.gameStatusText = gameStatusText; }

    public Integer getGameSequence() { return gameSequence; }
    public void setGameSequence(Integer gameSequence) { this.gameSequence = gameSequence; }

    public ZonedDateTime getGameDateEst() { return gameDateEst; }
    public void setGameDateEst(ZonedDateTime gameDateEst) { this.gameDateEst = gameDateEst; }

    public ZonedDateTime getGameTimeEst() { return gameTimeEst; }
    public void setGameTimeEst(ZonedDateTime gameTimeEst) { this.gameTimeEst = gameTimeEst; }

    public ZonedDateTime getGameDateTimeEst() { return gameDateTimeEst; }
    public void setGameDateTimeEst(ZonedDateTime gameDateTimeEst) { this.gameDateTimeEst = gameDateTimeEst; }

    public ZonedDateTime getGameDateUTC() { return gameDateUTC; }
    public void setGameDateUTC(ZonedDateTime gameDateUTC) { this.gameDateUTC = gameDateUTC; }

    public ZonedDateTime getGameTimeUTC() { return gameTimeUTC; }
    public void setGameTimeUTC(ZonedDateTime gameTimeUTC) { this.gameTimeUTC = gameTimeUTC; }

    public ZonedDateTime getGameDateTimeUTC() { return gameDateTimeUTC; }
    public void setGameDateTimeUTC(ZonedDateTime gameDateTimeUTC) { this.gameDateTimeUTC = gameDateTimeUTC; }

    public ZonedDateTime getAwayTeamTime() { return awayTeamTime; }
    public void setAwayTeamTime(ZonedDateTime awayTeamTime) { this.awayTeamTime = awayTeamTime; }

    public ZonedDateTime getHomeTeamTime() { return homeTeamTime; }
    public void setHomeTeamTime(ZonedDateTime homeTeamTime) { this.homeTeamTime = homeTeamTime; }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public Integer getMonthNum() { return monthNum; }
    public void setMonthNum(Integer monthNum) { this.monthNum = monthNum; }

    public Integer getWeekNumber() { return weekNumber; }
    public void setWeekNumber(Integer weekNumber) { this.weekNumber = weekNumber; }

    public String getWeekName() { return weekName; }
    public void setWeekName(String weekName) { this.weekName = weekName; }

    public String getIfNecessary() { return ifNecessary; }
    public void setIfNecessary(String ifNecessary) { this.ifNecessary = ifNecessary; }

    public String getSeriesGameNumber() { return seriesGameNumber; }
    public void setSeriesGameNumber(String seriesGameNumber) { this.seriesGameNumber = seriesGameNumber; }

    public String getGameLabel() { return gameLabel; }
    public void setGameLabel(String gameLabel) { this.gameLabel = gameLabel; }

    public String getGameSubLabel() { return gameSubLabel; }
    public void setGameSubLabel(String gameSubLabel) { this.gameSubLabel = gameSubLabel; }

    public String getSeriesText() { return seriesText; }
    public void setSeriesText(String seriesText) { this.seriesText = seriesText; }

    public String getArenaName() { return arenaName; }
    public void setArenaName(String arenaName) { this.arenaName = arenaName; }

    public String getArenaState() { return arenaState; }
    public void setArenaState(String arenaState) { this.arenaState = arenaState; }

    public String getArenaCity() { return arenaCity; }
    public void setArenaCity(String arenaCity) { this.arenaCity = arenaCity; }

    public String getPostponedStatus() { return postponedStatus; }
    public void setPostponedStatus(String postponedStatus) { this.postponedStatus = postponedStatus; }

    public String getBranchLink() { return branchLink; }
    public void setBranchLink(String branchLink) { this.branchLink = branchLink; }

    public String getGameSubtype() { return gameSubtype; }
    public void setGameSubtype(String gameSubtype) { this.gameSubtype = gameSubtype; }

    public Boolean getIsNeutral() { return isNeutral; }
    public void setIsNeutral(Boolean isNeutral) { this.isNeutral = isNeutral; }

    public Long getHomeTeamId() { return homeTeamId; }
    public void setHomeTeamId(Long homeTeamId) { this.homeTeamId = homeTeamId; }

    public Long getAwayTeamId() { return awayTeamId; }
    public void setAwayTeamId(Long awayTeamId) { this.awayTeamId = awayTeamId; }

    public Integer getHomeTeamScore() { return homeTeamScore; }
    public void setHomeTeamScore(Integer homeTeamScore) { this.homeTeamScore = homeTeamScore; }

    public Integer getAwayTeamScore() { return awayTeamScore; }
    public void setAwayTeamScore(Integer awayTeamScore) { this.awayTeamScore = awayTeamScore; }

    @Override
    public String toString() {
        return "Game [gameId=" + gameId + ", gameCode=" + gameCode + ", gameStatus=" + gameStatus +
               ", homeTeamId=" + homeTeamId + ", awayTeamId=" + awayTeamId +
               ", arenaName=" + arenaName + ", arenaCity=" + arenaCity + "]";
    }
}