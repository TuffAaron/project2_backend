package com.example.demo.service

import com.example.demo.service.GameService
import com.example.demo.model.Game
import com.example.demo.repository.GameRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import static org.junit.jupiter.api.Assertions.*
import static org.mockito.Mockito.*

@ExtendWith(MockitoExtension.class)
@DisplayName("GameService Tests")
class GameServiceTest {

    @Mock
    private GameRepository gameRepository

    @InjectMocks
    private GameService gameService

    private List<Game> sampleGames

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this)
        
        // Create sample games for testing
        sampleGames = []
        
        Game game1 = new Game("GAME001", "CODE001", 3, "Final")
        game1.setHomeTeamId(101L)
        game1.setAwayTeamId(102L)
        game1.setHomeTeamScore(110)
        game1.setAwayTeamScore(105)
        
        Game game2 = new Game("GAME002", "CODE002", 2, "In Progress")
        game2.setHomeTeamId(103L)
        game2.setAwayTeamId(101L)
        game2.setHomeTeamScore(85)
        game2.setAwayTeamScore(92)
        
        Game game3 = new Game("GAME003", "CODE003", 1, "Scheduled")
        game3.setHomeTeamId(104L)
        game3.setAwayTeamId(105L)
        game3.setHomeTeamScore(0)
        game3.setAwayTeamScore(0)
        
        sampleGames.addAll([game1, game2, game3])
    }

    @Test
    @DisplayName("Get all games should return all games from repository")
    void testGetAllGames() {
        // Arrange
        when(gameRepository.findAll()).thenReturn(sampleGames)

        // Act
        List<Game> result = gameService.getAllGames()

        // Assert
        assertNotNull(result)
        assertEquals(3, result.size())
        assertEquals("GAME001", result.get(0).getGameId())
        assertEquals("GAME002", result.get(1).getGameId())
        assertEquals("GAME003", result.get(2).getGameId())
        
        verify(gameRepository, times(1)).findAll()
    }

    @Test
    @DisplayName("Get all games should return empty list when no games exist")
    void testGetAllGamesEmpty() {
        // Arrange
        when(gameRepository.findAll()).thenReturn([])

        // Act
        List<Game> result = gameService.getAllGames()

        // Assert
        assertNotNull(result)
        assertTrue(result.isEmpty())
        
        verify(gameRepository, times(1)).findAll()
    }

    @Test
    @DisplayName("Get games by team should return games where team is home or away")
    void testGetGamesByTeam() {
        // Arrange
        Long teamId = 101L
        List<Game> teamGames = [sampleGames.get(0), sampleGames.get(1)] // Team 101 is in games 1 and 2
        when(gameRepository.findByHomeTeamIdOrAwayTeamId(teamId, teamId)).thenReturn(teamGames)

        // Act
        List<Game> result = gameService.getGamesByTeam(teamId)

        // Assert
        assertNotNull(result)
        assertEquals(2, result.size())
        
        // Verify team 101 is involved in both games
        assertTrue(result.get(0).getHomeTeamId() == 101L || result.get(0).getAwayTeamId() == 101L)
        assertTrue(result.get(1).getHomeTeamId() == 101L || result.get(1).getAwayTeamId() == 101L)
        
        verify(gameRepository, times(1)).findByHomeTeamIdOrAwayTeamId(teamId, teamId)
    }

    @Test
    @DisplayName("Get games by team should return empty list when team has no games")
    void testGetGamesByTeamNoGames() {
        // Arrange
        Long teamId = 999L
        when(gameRepository.findByHomeTeamIdOrAwayTeamId(teamId, teamId)).thenReturn([])

        // Act
        List<Game> result = gameService.getGamesByTeam(teamId)

        // Assert
        assertNotNull(result)
        assertTrue(result.isEmpty())
        
        verify(gameRepository, times(1)).findByHomeTeamIdOrAwayTeamId(teamId, teamId)
    }

    @Test
    @DisplayName("Get games by team should handle null team ID")
    void testGetGamesByTeamNullId() {
        // Arrange
        Long teamId = null
        when(gameRepository.findByHomeTeamIdOrAwayTeamId(teamId, teamId)).thenReturn([])

        // Act
        List<Game> result = gameService.getGamesByTeam(teamId)

        // Assert
        assertNotNull(result)
        assertTrue(result.isEmpty())
        
        verify(gameRepository, times(1)).findByHomeTeamIdOrAwayTeamId(teamId, teamId)
    }

    @Test
    @DisplayName("Get games by team should return only games involving the specified team")
    void testGetGamesByTeamFiltering() {
        // Arrange
        Long teamId = 103L
        List<Game> teamGames = [sampleGames.get(1)] // Only game 2 has team 103
        when(gameRepository.findByHomeTeamIdOrAwayTeamId(teamId, teamId)).thenReturn(teamGames)

        // Act
        List<Game> result = gameService.getGamesByTeam(teamId)

        // Assert
        assertNotNull(result)
        assertEquals(1, result.size())
        assertEquals("GAME002", result.get(0).getGameId())
        assertEquals(103L, result.get(0).getHomeTeamId())
        
        verify(gameRepository, times(1)).findByHomeTeamIdOrAwayTeamId(teamId, teamId)
    }

    @Test
    @DisplayName("Service should handle repository exceptions gracefully")
    void testRepositoryException() {
        // Arrange
        when(gameRepository.findAll()).thenThrow(new RuntimeException("Database connection error"))

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gameService.getAllGames()
        })
        
        assertEquals("Database connection error", exception.getMessage())
        verify(gameRepository, times(1)).findAll()
    }

    @Test
    @DisplayName("Service should handle repository exceptions for team games")
    void testGetGamesByTeamRepositoryException() {
        // Arrange
        Long teamId = 101L
        when(gameRepository.findByHomeTeamIdOrAwayTeamId(teamId, teamId))
            .thenThrow(new RuntimeException("Database query failed"))

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gameService.getGamesByTeam(teamId)
        })
        
        assertEquals("Database query failed", exception.getMessage())
        verify(gameRepository, times(1)).findByHomeTeamIdOrAwayTeamId(teamId, teamId)
    }

    @Test
    @DisplayName("Service methods should properly delegate to repository")
    void testRepositoryDelegation() {
        // Arrange
        when(gameRepository.findAll()).thenReturn(sampleGames)
        when(gameRepository.findByHomeTeamIdOrAwayTeamId(any(), any())).thenReturn(sampleGames.subList(0, 1))

        // Act
        gameService.getAllGames()
        gameService.getGamesByTeam(101L)

        // Assert - verify proper delegation
        verify(gameRepository, times(1)).findAll()
        verify(gameRepository, times(1)).findByHomeTeamIdOrAwayTeamId(101L, 101L)
        verifyNoMoreInteractions(gameRepository)
    }
}