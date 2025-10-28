package com.example.demo.dto;

import java.time.ZonedDateTime;

import com.example.demo.model.Game;

public class GameDTO {
    private String gameId;
    private ZonedDateTime gameDateTimeEst;
    private String gameStatusText;
    private TeamInfo homeTeam;
    private TeamInfo awayTeam;

    public static class TeamInfo{
        private Long teamId;
        private String teamName;
        private String teamTricode;
        private String logo;
        private Integer score;

        public TeamInfo(Long teamId, String teamName, String teamTricode, 
        String logo, Integer score){
            this.teamId = teamId;
            this.teamName = teamName;
            this.teamTricode = teamTricode;
            this.logo = logo;
            this.score = score;
        }

        public Long getTeamId() { return teamId; }
        public String getTeamName() { return teamName; }
        public String getTeamTricode() { return teamTricode; }
        public String getLogo() { return logo; }
        public Integer getScore() { return score; }
    }

    public GameDTO(String gameId, ZonedDateTime gameDateTimeEst, String gameStatusText, 
    TeamInfo homeTeam, TeamInfo awayTeam) {
        this.gameId = gameId;
        this.gameDateTimeEst = gameDateTimeEst;
        this.gameStatusText = gameStatusText;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public String getGameId() { return gameId; }
    public ZonedDateTime getGameDateTimeEst() { return gameDateTimeEst; }
    public String getGameStatusText() { return gameStatusText; }
    public TeamInfo getHomeTeam() { return homeTeam; }
    public TeamInfo getAwayTeam() { return awayTeam; }

}
