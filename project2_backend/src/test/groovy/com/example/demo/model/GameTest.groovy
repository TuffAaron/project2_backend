package com.example.demo.model

import com.example.demo.model.Game
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import static org.junit.jupiter.api.Assertions.*
import java.time.ZonedDateTime

@DisplayName("Game Model Tests")
class GameTest {

    private Game game
    private ZonedDateTime testDateTime

    @BeforeEach
    void setUp() {
        game = new Game()
        testDateTime = ZonedDateTime.now()
    }

    @Test
    @DisplayName("Default constructor should create empty Game")
    void testDefaultConstructor() {
        Game newGame = new Game()
        assertNotNull(newGame)
        assertNull(newGame.getGameId())
        assertNull(newGame.getGameCode())
        assertNull(newGame.getGameStatus())
        assertNull(newGame.getGameStatusText())
    }

    @Test
    @DisplayName("Parameterized constructor should set basic fields")
    void testParameterizedConstructor() {
        String gameId = "GAME123"
        String gameCode = "CODE456"
        Integer gameStatus = 3
        String gameStatusText = "Final"

        Game newGame = new Game(gameId, gameCode, gameStatus, gameStatusText)

        assertEquals(gameId, newGame.getGameId())
        assertEquals(gameCode, newGame.getGameCode())
        assertEquals(gameStatus, newGame.getGameStatus())
        assertEquals(gameStatusText, newGame.getGameStatusText())
    }

    @Test
    @DisplayName("Game ID setter and getter should work correctly")
    void testGameIdSetterGetter() {
        String gameId = "TEST_GAME_001"
        game.setGameId(gameId)
        assertEquals(gameId, game.getGameId())
    }

    @Test
    @DisplayName("Game code setter and getter should work correctly")
    void testGameCodeSetterGetter() {
        String gameCode = "TEST_CODE"
        game.setGameCode(gameCode)
        assertEquals(gameCode, game.getGameCode())
    }

    @Test
    @DisplayName("Game status setter and getter should work correctly")
    void testGameStatusSetterGetter() {
        Integer status = 2
        game.setGameStatus(status)
        assertEquals(status, game.getGameStatus())
    }

    @Test
    @DisplayName("Game status text setter and getter should work correctly")
    void testGameStatusTextSetterGetter() {
        String statusText = "In Progress"
        game.setGameStatusText(statusText)
        assertEquals(statusText, game.getGameStatusText())
    }

    @Test
    @DisplayName("Date time fields should handle ZonedDateTime correctly")
    void testDateTimeFields() {
        game.setGameDateEst(testDateTime)
        game.setGameTimeEst(testDateTime)
        game.setGameDateTimeEst(testDateTime)

        assertEquals(testDateTime, game.getGameDateEst())
        assertEquals(testDateTime, game.getGameTimeEst())
        assertEquals(testDateTime, game.getGameDateTimeEst())
    }

    @Test
    @DisplayName("Team ID fields should work correctly")
    void testTeamIdFields() {
        Long homeTeamId = 101L
        Long awayTeamId = 102L

        game.setHomeTeamId(homeTeamId)
        game.setAwayTeamId(awayTeamId)

        assertEquals(homeTeamId, game.getHomeTeamId())
        assertEquals(awayTeamId, game.getAwayTeamId())
    }

    @Test
    @DisplayName("Team score fields should work correctly")
    void testTeamScoreFields() {
        Integer homeScore = 85
        Integer awayScore = 92

        game.setHomeTeamScore(homeScore)
        game.setAwayTeamScore(awayScore)

        assertEquals(homeScore, game.getHomeTeamScore())
        assertEquals(awayScore, game.getAwayTeamScore())
    }

    @Test
    @DisplayName("Arena information fields should work correctly")
    void testArenaFields() {
        String arenaName = "Test Arena"
        String arenaCity = "Test City"
        String arenaState = "TS"

        game.setArenaName(arenaName)
        game.setArenaCity(arenaCity)
        game.setArenaState(arenaState)

        assertEquals(arenaName, game.getArenaName())
        assertEquals(arenaCity, game.getArenaCity())
        assertEquals(arenaState, game.getArenaState())
    }

    @Test
    @DisplayName("Boolean fields should handle null and boolean values")
    void testBooleanFields() {
        // Test null
        game.setIsNeutral(null)
        assertNull(game.getIsNeutral())

        // Test true
        game.setIsNeutral(true)
        assertTrue(game.getIsNeutral())

        // Test false
        game.setIsNeutral(false)
        assertFalse(game.getIsNeutral())
    }

    @Test
    @DisplayName("toString should return meaningful string representation")
    void testToString() {
        game.setGameId("TEST123")
        game.setGameCode("TC123")
        
        String result = game.toString()
        assertNotNull(result)
        assertTrue(result.contains("TEST123"))
        assertTrue(result.contains("TC123"))
    }
}