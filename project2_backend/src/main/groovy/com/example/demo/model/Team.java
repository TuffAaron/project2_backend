package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "teams")
public class Team {
    
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "logo", length = 500)
    private String logo;

    public Team(){
    }

    public Team(Long id, String name, String nickname, String logo){
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.logo = logo;
    }

    public Long getId(){ return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name){ this.name = name; }

    public String getNickname(){ return nickname; }

    public void setNickname(String nickname){ this.nickname = nickname; }

    public String getLogo(){ return logo; }

    public void setLogo(String logo){ this.logo = logo; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id != null && id.equals(team.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Team{"+
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
