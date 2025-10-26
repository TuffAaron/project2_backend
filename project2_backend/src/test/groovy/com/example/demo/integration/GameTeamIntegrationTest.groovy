package com.example.demo.integration

import com.example.demo.model.Game
import com.example.demo.model.Team
import com.example.demo.repository.GameRepository
import com.example.demo.repository.TeamRepository
import com.example.demo.service.GameService
import com.example.demo.service.TeamService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import static org.junit.jupiter.api.Assertions.*

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("Game and Team Integration Tests")
class GameTeamIntegrationTest {

    @Autowired
    private GameRepository gameRepository

    @Autowired
    private TeamRepository teamRepository

    @Autowired
    private GameService gameService

    @Autowired
    private TeamService teamService

    private Team lakers
    private Team warriors
    private Game game1

    @BeforeEach
    void setUp() {
        // Clean up before each test
        gameRepository.deleteAll()
        teamRepository.deleteAll()

        // Create and save test teams
        lakers = new Team(101L, "Lakers", "Los Angeles", "LAL", "lakers")
        lakers.setWins(45)
        lakers.setLosses(37)
        lakers.setSeed(7)

        warriors = new Team(102L, "Warriors", "Golden State", "GSW", "warriors")
        warriors.setWins(53)
        warriors.setLosses(29)
        warriors.setSeed(3)

        teamRepository.save(lakers)
        teamRepository.save(warriors)

        // Create and save test game
        game1 = new Game("GAME001", "CODE001", 3, "Final")
        game1.setHomeTeamId(lakers.getTeamId())
        game1.setAwayTeamId(warriors.getTeamId())
        game1.setHomeTeamScore(110)
        game1.setAwayTeamScore(105)
        game1.setArenaName("Crypto.com Arena")
        game1.setArenaCity("Los Angeles")

        gameRepository.save(game1)
    }

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll()
        teamRepository.deleteAll()
    }

    @Test
    @DisplayName("Full game lifecycle - create, read, update, delete")
    void testFullGameLifecycle() {
        // CREATE - Already created in setUp

        // READ
        Game foundGame = gameService.getGameById("GAME001")
        assertNotNull(foundGame)
        assertEquals("GAME001", foundGame.getGameId())
        assertEquals(110, foundGame.getHomeTeamScore())

        // UPDATE
        foundGame.setHomeTeamScore(115)
        foundGame.setAwayTeamScore(108)
        Game updatedGame = gameService.updateGame("GAME001", foundGame)
        assertEquals(115, updatedGame.getHomeTeamScore())
        assertEquals(108, updatedGame.getAwayTeamScore())

        // DELETE
        gameService.deleteGame("GAME001")
        assertNull(gameService.getGameById("GAME001"))
    }

    @Test
    @DisplayName("Team should have games associated with it")
    void testTeamWithGames() {
        // Arrange - Create another game for Lakers
        Game game2 = new Game("GAME002", "CODE002", 2, "In Progress")
        game2.setHomeTeamId(lakers.getTeamId())
        game2.setAwayTeamId(103L)
        game2.setHomeTeamScore(88)
        game2.setAwayTeamScore(92)
        gameRepository.save(game2)

        // Act
        List<Game> lakersGames = gameService.getGamesByTeam(lakers.getTeamId())

        // Assert
        assertNotNull(lakersGames)
        assertEquals(2, lakersGames.size())
        
        lakersGames.forEach { game ->
            assertTrue(game.getHomeTeamId() == lakers.getTeamId() || 
                      game.getAwayTeamId() == lakers.getTeamId())
        }
    }

    @Test
    @DisplayName("Game should reference valid teams")
    void testGameReferencesValidTeams() {
        // Act
        Game game = gameService.getGameById("GAME001")
        Team homeTeam = teamRepository.findById(game.getHomeTeamId()).orElse(null)
        Team awayTeam = teamRepository.findById(game.getAwayTeamId()).orElse(null)

        // Assert
        assertNotNull(homeTeam)
        assertNotNull(awayTeam)
        assertEquals("Lakers", homeTeam.getTeamName())
        assertEquals("Warriors", awayTeam.getTeamName())
    }

    @Test
    @DisplayName("Service layer should retrieve all teams and games")
    void testGetAllTeamsAndGames() {
        // Act
        List<Team> allTeams = teamService.getAllTeams()
        List<Game> allGames = gameService.getAllGames()

        // Assert
        assertNotNull(allTeams)
        assertNotNull(allGames)
        assertTrue(allTeams.size() >= 2)
        assertTrue(allGames.size() >= 1)
    }

    @Test
    @DisplayName("Multiple games between same teams should be stored correctly")
    void testMultipleGamesBetweenTeams() {
        // Arrange
        Game game2 = new Game("GAME002", "CODE002", 2, "Scheduled")
        game2.setHomeTeamId(warriors.getTeamId())
        game2.setAwayTeamId(lakers.getTeamId())
        game2.setHomeTeamScore(0)
        game2.setAwayTeamScore(0)
        gameRepository.save(game2)

        // Act
        List<Game> lakersGames = gameService.getGamesByTeam(lakers.getTeamId())
        List<Game> warriorsGames = gameService.getGamesByTeam(warriors.getTeamId())

        // Assert
        assertEquals(2, lakersGames.size())
        assertEquals(2, warriorsGames.size())
    }

    @Test
    @DisplayName("Game with detailed arena information should be saved and retrieved")
    void testGameWithArenaDetails() {
        // Act
        Game game = gameService.getGameById("GAME001")

        // Assert
        assertNotNull(game)
        assertEquals("Crypto.com Arena", game.getArenaName())
        assertEquals("Los Angeles", game.getArenaCity())
    }

    @Test
    @DisplayName("Team statistics should be persisted correctly")
    void testTeamStatisticsPersistence() {
        // Act
        List<Team> teams = teamService.getAllTeams()

        // Assert
        Team foundLakers = teams.find { it.teamId == 101L }
        assertNotNull(foundLakers)
        assertEquals(45, foundLakers.getWins())
        assertEquals(37, foundLakers.getLosses())
        assertEquals(7, foundLakers.getSeed())
    }

    @Test
    @DisplayName("Creating game with non-existent team should still save")
    void testGameWithNonExistentTeam() {
        // Arrange
        Game game = new Game("GAME003", "CODE003", 1, "Scheduled")
        game.setHomeTeamId(999L) // Non-existent team
        game.setAwayTeamId(lakers.getTeamId())

        // Act
        Game savedGame = gameRepository.save(game)

        // Assert
        assertNotNull(savedGame)
        assertEquals("GAME003", savedGame.getGameId())
        assertEquals(999L, savedGame.getHomeTeamId())
    }

    @Test
    @DisplayName("Games by team should return both home and away games")
    void testGamesByTeamIncludesBothHomeAndAway() {
        // Arrange - Create away game for Lakers
        Game awayGame = new Game("GAME004", "CODE004", 1, "Scheduled")
        awayGame.setHomeTeamId(103L)
        awayGame.setAwayTeamId(lakers.getTeamId())
        gameRepository.save(awayGame)

        // Act
        List<Game> lakersGames = gameService.getGamesByTeam(lakers.getTeamId())

        // Assert
        assertTrue(lakersGames.size() >= 2)
        boolean hasHomeGame = lakersGames.any { it.homeTeamId == lakers.getTeamId() }
        boolean hasAwayGame = lakersGames.any { it.awayTeamId == lakers.getTeamId() }
        assertTrue(hasHomeGame)
        assertTrue(hasAwayGame)
    }

    @Test
    @DisplayName("Deleting a team should not delete associated games")
    void testDeletingTeamDoesNotDeleteGames() {
        // Act
        teamRepository.deleteById(lakers.getTeamId())
        List<Game> games = gameService.getAllGames()

        // Assert
        assertTrue(games.size() >= 1)
        Game game = games.find { it.gameId == "GAME001" }
        assertNotNull(game)
        assertEquals(101L, game.getHomeTeamId())
    }

    @Test
    @DisplayName("Repository and Service layer should return consistent data")
    void testRepositoryAndServiceConsistency() {
        // Act
        List<Game> gamesFromService = gameService.getAllGames()
        List<Game> gamesFromRepository = gameRepository.findAll()

        // Assert
        assertEquals(gamesFromRepository.size(), gamesFromService.size())
    }

    @Test
    @DisplayName("Update game scores should persist correctly")
    void testUpdateGameScores() {
        // Arrange
        Game game = gameService.getGameById("GAME001")
        
        // Act
        game.setHomeTeamScore(120)
        game.setAwayTeamScore(118)
        game.setGameStatusText("Final - OT")
        Game updatedGame = gameRepository.save(game)

        // Assert
        Game refetchedGame = gameService.getGameById("GAME001")
        assertEquals(120, refetchedGame.getHomeTeamScore())
        assertEquals(118, refetchedGame.getAwayTeamScore())
        assertEquals("Final - OT", refetchedGame.getGameStatusText())
    }

    @Test
    @DisplayName("Team repository findById should work with service results")
    void testTeamRepositoryWithServiceResults() {
        // Act
        List<Team> allTeams = teamService.getAllTeams()
        Team firstTeam = allTeams.get(0)
        Team foundTeam = teamRepository.findById(firstTeam.getTeamId()).orElse(null)

        // Assert
        assertNotNull(foundTeam)
        assertEquals(firstTeam.getTeamId(), foundTeam.getTeamId())
        assertEquals(firstTeam.getTeamName(), foundTeam.getTeamName())
    }
}
