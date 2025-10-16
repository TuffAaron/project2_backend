package com.example.demo.controller;
import java.util.List;
import com.example.demo.model.Team;
import com.example.demo.service.TeamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Profile;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "*")
@Profile("!heroku")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/all")
    public List<Team> getAllTeams(){
        return teamService.getAllTeams();
    }
}
