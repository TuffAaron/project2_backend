package com.example.demo.repository

import com.example.demo.model.Game
import com.example.demo.repository.GameRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ActiveProfiles
import com.example.demo.Project2BackendApplication
import static org.junit.jupiter.api.Assertions.*

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = [Project2BackendApplication.class])
@DisplayName("GameRepository Integration Tests")
class GameRepositoryTest {

    @Autowired
    private TestEntityManager entityManager

    @Autowired
    private GameRepository gameRepository

    private Game game1, game2, game3

    @BeforeEach
    void setUp() {
        // Create test games
        game1 = new Game("GAME001", "CODE001", 3, "Final")
        game1.setHomeTeamId(101L)
        game1.setAwayTeamId(102L)
        game1.setHomeTeamScore(110)
        game1.setAwayTeamScore(105)

        game2 = new Game("GAME002", "CODE002", 2, "In Progress")
        game2.setHomeTeamId(103L)
        game2.setAwayTeamId(101L)
        game2.setHomeTeamScore(85)
        game2.setAwayTeamScore(92)

        game3 = new Game("GAME003", "CODE003", 1, "Scheduled")
        game3.setHomeTeamId(104L)
        game3.setAwayTeamId(105L)
        game3.setHomeTeamScore(0)
        game3.setAwayTeamScore(0)

        // Persist test data
        entityManager.persistAndFlush(game1)
        entityManager.persistAndFlush(game2)
        entityManager.persistAndFlush(game3)
    }

    @Test
    @DisplayName("Find all games should return all persisted games")
    void testFindAll() {
        // Act
        List<Game> games = gameRepository.findAll()

        // Assert
        assertNotNull(games)
        assertEquals(3, games.size())
        
        List<String> gameIds = games.collect { it.gameId }
        assertTrue(gameIds.contains("GAME001"))
        assertTrue(gameIds.contains("GAME002"))
        assertTrue(gameIds.contains("GAME003"))
    }

    @Test
    @DisplayName("Find by home team ID should return games where team is home")
    void testFindByHomeTeamIdOrAwayTeamId_HomeTeam() {
        // Act - Find games where team 101 is involved
        List<Game> games = gameRepository.findByHomeTeamIdOrAwayTeamId(101L, 101L)

        // Assert
        assertNotNull(games)
        assertEquals(2, games.size())
        
        // Verify team 101 is involved in both games
        games.forEach { game ->
            assertTrue(game.homeTeamId == 101L || game.awayTeamId == 101L)
        }
    }

    @Test
    @DisplayName("Find by away team ID should return games where team is away")
    void testFindByHomeTeamIdOrAwayTeamId_AwayTeam() {
        // Act - Find games where team 102 is involved (only away in game1)
        List<Game> games = gameRepository.findByHomeTeamIdOrAwayTeamId(102L, 102L)

        // Assert
        assertNotNull(games)
        assertEquals(1, games.size())
        assertEquals("GAME001", games.get(0).gameId)
        assertEquals(102L, games.get(0).awayTeamId)
    }

    @Test
    @DisplayName("Find by team ID should return empty list for non-existent team")
    void testFindByHomeTeamIdOrAwayTeamId_NonExistentTeam() {
        // Act
        List<Game> games = gameRepository.findByHomeTeamIdOrAwayTeamId(999L, 999L)

        // Assert
        assertNotNull(games)
        assertTrue(games.isEmpty())
    }

    @Test
    @DisplayName("Find by ID should return specific game")
    void testFindById() {
        // Act
        Optional<Game> gameOptional = gameRepository.findById("GAME001")

        // Assert
        assertTrue(gameOptional.isPresent())
        Game game = gameOptional.get()
        assertEquals("GAME001", game.gameId)
        assertEquals("CODE001", game.gameCode)
        assertEquals(3, game.gameStatus)
        assertEquals("Final", game.gameStatusText)
    }

    @Test
    @DisplayName("Save should persist new game")
    void testSave() {
        // Arrange
        Game newGame = new Game("GAME004", "CODE004", 0, "Not Started")
        newGame.setHomeTeamId(106L)
        newGame.setAwayTeamId(107L)

        // Act
        Game savedGame = gameRepository.save(newGame)

        // Assert
        assertNotNull(savedGame)
        assertEquals("GAME004", savedGame.gameId)
        
        // Verify it's in the database
        Optional<Game> foundGame = gameRepository.findById("GAME004")
        assertTrue(foundGame.isPresent())
        assertEquals("CODE004", foundGame.get().gameCode)
    }

    @Test
    @DisplayName("Delete should remove game from database")
    void testDelete() {
        // Arrange
        assertTrue(gameRepository.findById("GAME001").isPresent())

        // Act
        gameRepository.deleteById("GAME001")

        // Assert
        assertFalse(gameRepository.findById("GAME001").isPresent())
        assertEquals(2, gameRepository.findAll().size())
    }

    @Test
    @DisplayName("Count should return correct number of games")
    void testCount() {
        // Act
        long count = gameRepository.count()

        // Assert
        assertEquals(3L, count)
    }

    @Test
    @DisplayName("Exists by ID should return true for existing game")
    void testExistsById() {
        // Act & Assert
        assertTrue(gameRepository.existsById("GAME001"))
        assertTrue(gameRepository.existsById("GAME002"))
        assertTrue(gameRepository.existsById("GAME003"))
        assertFalse(gameRepository.existsById("NONEXISTENT"))
    }
}