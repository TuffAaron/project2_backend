package main.groovy.com.example.demo.loader;
import com.example.demo.model.Game;
import com.example.demo.model.Team;
import com.example.demo.repository.GameRepository;
import com.example.demo.repository.TeamRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "app.data.load.enabled", havingValue = "true", matchIfMissing = false)
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ObjectMapper objectMapper;

@Override
public void run(String... args) throws Exception {
    try {
        // Only load data if database is empty and we're not on Heroku
        if (isDataLoadingEnabled() && isDatabaseEmpty()) {
            System.out.println("Loading data from JSON...");

            loadScheduleData();

            System.out.println("Data load completed successfully!");
            System.out.println("Total teams loaded: " + teamRepository.count());
            System.out.println("Total games loaded: " + gameRepository.count());
        } else {
            System.out.println("Data loading skipped. Database may already contain data or loading is disabled.");
        }
    } catch (Exception e) {
        System.err.println("Error during data loading, continuing without data load: " + e.getMessage());
        // Don't crash the application if data loading fails
    }
}

    private void loadScheduleData() throws IOException {
        // reading the JSON file
        ClassPathResource resource = new ClassPathResource("scheduleLeagueV2.json");
        JsonNode rootNode = objectMapper.readTree(resource.getInputStream());
        
        // navigating to leagueSchedule
        JsonNode leagueSchedule = rootNode.get("leagueSchedule");
        if (leagueSchedule == null) {
            throw new RuntimeException("leagueSchedule not found in JSON");
        }

        // Use a map to store unique teams (key: teamId)
        Map<Long, Team> teamsMap = new HashMap<>();

        // Navigate to gameDates array
        JsonNode gameDates = leagueSchedule.get("gameDates");
        if (gameDates == null || !gameDates.isArray()) {
            throw new RuntimeException("gameDates array not found");
        }

        // each game date
        for (JsonNode gameDateNode : gameDates) {
            JsonNode games = gameDateNode.get("games");
            if (games == null || !games.isArray()) {
                continue;
            }

            // each game
            for (JsonNode gameNode : games) {
                // extract teams
                extractTeam(gameNode.get("homeTeam"), teamsMap);
                extractTeam(gameNode.get("awayTeam"), teamsMap);

                // save the game to database
                Game game = createGame(gameNode);
                gameRepository.save(game);
            }
        }

        // save all unique teams to database
        teamRepository.saveAll(teamsMap.values());
        
        System.out.println("Extracted " + teamsMap.size() + " unique teams");
    }

        //method to add teams to map
    private void extractTeam(JsonNode teamNode, Map<Long, Team> teamsMap) {
        if (teamNode == null) {
            return;
        }

        Long teamId = teamNode.get("teamId").asLong();
        
        // no duplicate teams
        if (!teamsMap.containsKey(teamId)) {
            Team team = new Team();
            team.setTeamId(teamId);
            team.setTeamName(getStringValue(teamNode, "teamName"));
            team.setTeamCity(getStringValue(teamNode, "teamCity"));
            team.setTeamTricode(getStringValue(teamNode, "teamTricode"));
            team.setTeamSlug(getStringValue(teamNode, "teamSlug"));
            team.setWins(getIntValue(teamNode, "wins"));
            team.setLosses(getIntValue(teamNode, "losses"));
            team.setScore(getIntValue(teamNode, "score"));
            team.setSeed(getIntValue(teamNode, "seed"));
            
            teamsMap.put(teamId, team);
        }
    }

    private Game createGame(JsonNode gameNode) {
        Game game = new Game();
        
        // game info
        game.setGameId(getStringValue(gameNode, "gameId"));
        game.setGameCode(getStringValue(gameNode, "gameCode"));
        game.setGameStatus(getIntValue(gameNode, "gameStatus"));
        game.setGameStatusText(getStringValue(gameNode, "gameStatusText"));
        game.setGameSequence(getIntValue(gameNode, "gameSequence"));
        
        // date and time fields
        game.setGameDateEst(parseZonedDateTime(gameNode, "gameDateEst"));
        game.setGameTimeEst(parseZonedDateTime(gameNode, "gameTimeEst"));
        game.setGameDateTimeEst(parseZonedDateTime(gameNode, "gameDateTimeEst"));
        game.setGameDateUTC(parseZonedDateTime(gameNode, "gameDateUTC"));
        game.setGameTimeUTC(parseZonedDateTime(gameNode, "gameTimeUTC"));
        game.setGameDateTimeUTC(parseZonedDateTime(gameNode, "gameDateTimeUTC"));
        game.setAwayTeamTime(parseZonedDateTime(gameNode, "awayTeamTime"));
        game.setHomeTeamTime(parseZonedDateTime(gameNode, "homeTeamTime"));
        
        // Game details
        game.setDay(getStringValue(gameNode, "day"));
        game.setMonthNum(getIntValue(gameNode, "monthNum"));
        game.setWeekNumber(getIntValue(gameNode, "weekNumber"));
        game.setWeekName(getStringValue(gameNode, "weekName"));
        game.setIfNecessary(getStringValue(gameNode, "ifNecessary"));
        game.setSeriesGameNumber(getStringValue(gameNode, "seriesGameNumber"));
        game.setGameLabel(getStringValue(gameNode, "gameLabel"));
        game.setGameSubLabel(getStringValue(gameNode, "gameSubLabel"));
        game.setSeriesText(getStringValue(gameNode, "seriesText"));
        
    
        game.setArenaName(getStringValue(gameNode, "arenaName"));
        game.setArenaState(getStringValue(gameNode, "arenaState"));
        game.setArenaCity(getStringValue(gameNode, "arenaCity"));
        
   
        game.setPostponedStatus(getStringValue(gameNode, "postponedStatus"));
        game.setBranchLink(getStringValue(gameNode, "branchLink"));
        game.setGameSubtype(getStringValue(gameNode, "gameSubtype"));
        game.setIsNeutral(getBooleanValue(gameNode, "isNeutral"));
        
        // team IDs and scores
        JsonNode homeTeam = gameNode.get("homeTeam");
        JsonNode awayTeam = gameNode.get("awayTeam");
        
        if (homeTeam != null) {
            game.setHomeTeamId(homeTeam.get("teamId").asLong());
            game.setHomeTeamScore(getIntValue(homeTeam, "score"));
        }
        
        if (awayTeam != null) {
            game.setAwayTeamId(awayTeam.get("teamId").asLong());
            game.setAwayTeamScore(getIntValue(awayTeam, "score"));
        }
        
        return game;
    }

    //helper methods
    private String getStringValue(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asText() : null;
    }

    private Integer getIntValue(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asInt() : null;
    }

    private Boolean getBooleanValue(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asBoolean() : null;
    }

    private ZonedDateTime parseZonedDateTime(JsonNode node, String fieldName) {
        String dateTimeStr = getStringValue(node, fieldName);
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        
        try {
            return ZonedDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            System.err.println("Failed to parse datetime: " + dateTimeStr + " for field: " + fieldName);
            return null;
        }
    }

    private boolean isDataLoadingEnabled() {
        // Disable data loading on Heroku or when explicitly disabled
        String herokuApp = System.getenv("HEROKU_APP_NAME");
        String jawsdbUrl = System.getenv("JAWSDB_URL");
        return herokuApp == null && jawsdbUrl == null;
    }

    private boolean isDatabaseEmpty() {
        try {
            return teamRepository.count() == 0;
        } catch (Exception e) {
            System.err.println("Could not check database status: " + e.getMessage());
            return false;
        }
    }
}
