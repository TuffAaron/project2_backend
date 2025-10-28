package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "teams")
public class Team {
    
    @Id
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "team_city")
    private String teamCity;

    @Column(name = "team_tricode")
    private String teamTricode;

    @Column(name = "team_slug")
    private String teamSlug;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "logo", length = 500)
    private String logo;

    @Column(name = "wins")
    private Integer wins;

    @Column(name = "losses")
    private Integer losses;

    @Column(name = "score")
    private Integer score;

    @Column(name = "seed")
    private Integer seed;

    // Constructors
    public Team(){
    }

    public Team(Long teamId, String teamName, String teamCity, String teamTricode, String teamSlug){
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamCity = teamCity;
        this.teamTricode = teamTricode;
        this.teamSlug = teamSlug;
    }

    // Getters and Setters
    public Long getTeamId(){ return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName){ this.teamName = teamName; }

    public String getTeamCity() { return teamCity; }
    public void setTeamCity(String teamCity) { this.teamCity = teamCity; }

    public String getTeamTricode() { return teamTricode; }
    public void setTeamTricode(String teamTricode) { this.teamTricode = teamTricode; }

    public String getTeamSlug() { return teamSlug; }
    public void setTeamSlug(String teamSlug) { this.teamSlug = teamSlug; }

    public String getNickname(){ return nickname; }
    public void setNickname(String nickname){ this.nickname = nickname; }

    public String getLogo(){ return logo; }
    public void setLogo(String logo){ this.logo = logo; }

    public Integer getWins() { return wins; }
    public void setWins(Integer wins) { this.wins = wins; }

    public Integer getLosses() { return losses; }
    public void setLosses(Integer losses) { this.losses = losses; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Integer getSeed() { return seed; }
    public void setSeed(Integer seed) { this.seed = seed; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamId != null && teamId.equals(team.teamId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamCity='" + teamCity + '\'' +
                ", teamTricode='" + teamTricode + '\'' +
                ", teamSlug='" + teamSlug + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                '}';
    }
}
