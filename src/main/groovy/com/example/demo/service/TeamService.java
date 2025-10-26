package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Team;
import com.example.demo.repository.TeamRepository;
import java.util.List;

@Service
public class TeamService {
    
    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }
}
