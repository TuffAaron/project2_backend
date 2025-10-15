package com.example.demo.controller

import com.example.demo.controller.GameController
import com.example.demo.model.Game
import com.example.demo.service.GameService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import static org.junit.jupiter.api.Assertions.*
import static org.mockito.Mockito.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@DisplayName("GameController Tests")
class GameControllerTest {

    private MockMvc mockMvc

    @Mock
    private GameService gameService

    @InjectMocks
    private GameController gameController

    private List<Game> sampleGames

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build()
        
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
        
        sampleGames.add(game1)
        sampleGames.add(game2)
    }

    @Test
    @DisplayName("Test endpoint should return success message")
    void testTestEndpoint() {
        String result = gameController.testEndpoint()
        assertEquals("API is working! GameController is responding.", result)
    }

    @Test
    @DisplayName("Test endpoint should return 200 status via MockMvc")
    void testTestEndpointWithMockMvc() throws Exception {
        mockMvc.perform(get("/api/games/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("API is working! GameController is responding."))
    }

    @Test
    @DisplayName("Get all games should return list of games")
    void testGetAllGames() {
        // Arrange
        when(gameService.getAllGames()).thenReturn(sampleGames)

        // Act
        List<Game> result = gameController.getAllGames()

        // Assert
        assertNotNull(result)
        assertEquals(2, result.size())
        assertEquals("GAME001", result.get(0).getGameId())
        assertEquals("GAME002", result.get(1).getGameId())
        
        verify(gameService, times(1)).getAllGames()
    }

    @Test
    @DisplayName("Get all games should return 200 status via MockMvc")
    void testGetAllGamesWithMockMvc() throws Exception {
        // Arrange
        when(gameService.getAllGames()).thenReturn(sampleGames)

        // Act & Assert
        mockMvc.perform(get("/api/games/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.length()').value(2))
                .andExpect(jsonPath('$[0].gameId').value("GAME001"))
                .andExpect(jsonPath('$[1].gameId').value("GAME002"))
        
        verify(gameService, times(1)).getAllGames()
    }

    @Test
    @DisplayName("Get games by team should return filtered games")
    void testGetGamesByTeam() {
        // Arrange
        Long teamId = 101L
        List<Game> teamGames = [sampleGames.get(0), sampleGames.get(1)] // Both games involve team 101
        when(gameService.getGamesByTeam(teamId)).thenReturn(teamGames)

        // Act
        List<Game> result = gameController.getGamesByTeam(teamId)

        // Assert
        assertNotNull(result)
        assertEquals(2, result.size())
        verify(gameService, times(1)).getGamesByTeam(teamId)
    }

    @Test
    @DisplayName("Get games by team should return 200 status via MockMvc")
    void testGetGamesByTeamWithMockMvc() throws Exception {
        // Arrange
        Long teamId = 101L
        List<Game> teamGames = [sampleGames.get(0)]
        when(gameService.getGamesByTeam(teamId)).thenReturn(teamGames)

        // Act & Assert
        mockMvc.perform(get("/api/games/team/{teamId}", teamId))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.length()').value(1))
                .andExpect(jsonPath('$[0].gameId').value("GAME001"))
        
        verify(gameService, times(1)).getGamesByTeam(teamId)
    }

    @Test
    @DisplayName("Get games by team should handle service exceptions")
    void testGetGamesByTeamWithException() {
        // Arrange
        Long teamId = 999L
        when(gameService.getGamesByTeam(teamId)).thenThrow(new RuntimeException("Team not found"))

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gameController.getGamesByTeam(teamId)
        })
        
        assertTrue(exception.getMessage().contains("Error fetching games for team 999"))
        assertTrue(exception.getMessage().contains("Team not found"))
        verify(gameService, times(1)).getGamesByTeam(teamId)
    }

    @Test
    @DisplayName("Get games by team should return 500 status for service exceptions via MockMvc")
    void testGetGamesByTeamWithExceptionMockMvc() throws Exception {
        // Arrange
        Long teamId = 999L
        when(gameService.getGamesByTeam(teamId)).thenThrow(new RuntimeException("Team not found"))

        // Act & Assert
        mockMvc.perform(get("/api/games/team/{teamId}", teamId))
                .andExpect(status().is5xxServerError())
        
        verify(gameService, times(1)).getGamesByTeam(teamId)
    }

    @Test
    @DisplayName("Get games by team should handle empty results")
    void testGetGamesByTeamEmptyResults() {
        // Arrange
        Long teamId = 999L
        when(gameService.getGamesByTeam(teamId)).thenReturn([])

        // Act
        List<Game> result = gameController.getGamesByTeam(teamId)

        // Assert
        assertNotNull(result)
        assertTrue(result.isEmpty())
        verify(gameService, times(1)).getGamesByTeam(teamId)
    }

    @Test
    @DisplayName("Controller should have proper CORS configuration")
    void testCorsConfiguration() {
        // This test verifies that the @CrossOrigin annotation is present
        // In a real application, you might test this with integration tests
        assertTrue(gameController.getClass().isAnnotationPresent(org.springframework.web.bind.annotation.CrossOrigin.class))
    }

    @Test
    @DisplayName("Controller should have proper RequestMapping")
    void testRequestMapping() {
        // Verify the controller has the correct request mapping
        assertTrue(gameController.getClass().isAnnotationPresent(org.springframework.web.bind.annotation.RequestMapping.class))
        org.springframework.web.bind.annotation.RequestMapping mapping = 
            gameController.getClass().getAnnotation(org.springframework.web.bind.annotation.RequestMapping.class)
        assertEquals("/api/games", mapping.value()[0])
    }
}