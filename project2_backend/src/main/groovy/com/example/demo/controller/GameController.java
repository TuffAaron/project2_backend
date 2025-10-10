package com.example.demo.controller;

import java.time.LocalDate;
import com.example.demo.model.Game;
import java.util.List;
import com.example.demo.service.GameService;
import java.time.ZonedDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameController {
    
    @Autowired
    private GameService gameService;

    @GetMapping("/team/{teamId}")
    public List<Game> getGamesByTeamAndDate(
        @PathVariable Long teamId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){
        ZonedDateTime startDateTime = startDate.atStartOfDay(ZoneId.of("America/New_York"));
        ZonedDateTime endDateTime = endDate.atTime(23, 59, 59).atZone(ZoneId.of("America/New_York"));
        return gameService.getGamesByTeamAndDate(teamId, startDateTime, endDateTime);
    }
}
