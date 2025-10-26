package com.example.demo.controller

import com.example.demo.controller.TeamController
import com.example.demo.model.Team
import com.example.demo.service.TeamService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import static org.junit.jupiter.api.Assertions.*
import static org.mockito.Mockito.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(TeamController.class)
@ActiveProfiles("test")
@DisplayName("TeamController Tests")
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc

    @MockBean
    private TeamService teamService

    @Autowired
    private TeamController teamController

    private List<Team> sampleTeams

    @BeforeEach
    void setUp() {
        // Create sample teams for testing
        sampleTeams = []
        
        Team team1 = new Team(101L, "Lakers", "Los Angeles", "LAL", "lakers")
        team1.setWins(45)
        team1.setLosses(37)
        team1.setScore(110)
        team1.setSeed(7)
        
        Team team2 = new Team(102L, "Warriors", "Golden State", "GSW", "warriors")
        team2.setWins(53)
        team2.setLosses(29)
        team2.setScore(115)
        team2.setSeed(3)
        
        Team team3 = new Team(103L, "Celtics", "Boston", "BOS", "celtics")
        team3.setWins(57)
        team3.setLosses(25)
        team3.setScore(108)
        team3.setSeed(1)
        
        sampleTeams.add(team1)
        sampleTeams.add(team2)
        sampleTeams.add(team3)
    }

    @Test
    @DisplayName("Get all teams should return list of teams")
    void testGetAllTeams() {
        // Arrange
        when(teamService.getAllTeams()).thenReturn(sampleTeams)

        // Act
        List<Team> result = teamController.getAllTeams()

        // Assert
        assertNotNull(result)
        assertEquals(3, result.size())
        assertEquals("Lakers", result.get(0).getTeamName())
        assertEquals("Warriors", result.get(1).getTeamName())
        assertEquals("Celtics", result.get(2).getTeamName())
        
        verify(teamService, times(1)).getAllTeams()
    }

    @Test
    @DisplayName("Get all teams should return 200 status via MockMvc")
    void testGetAllTeamsWithMockMvc() throws Exception {
        // Arrange
        when(teamService.getAllTeams()).thenReturn(sampleTeams)

        // Act & Assert
        mockMvc.perform(get("/api/teams/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isArray())
                .andExpect(jsonPath('$.length()').value(3))
                .andExpect(jsonPath('$[0].teamName').value("Lakers"))
                .andExpect(jsonPath('$[0].teamCity').value("Los Angeles"))
                .andExpect(jsonPath('$[1].teamName').value("Warriors"))
                .andExpect(jsonPath('$[2].teamName').value("Celtics"))
        
        verify(teamService, times(1)).getAllTeams()
    }

    @Test
    @DisplayName("Get all teams should return empty list when no teams exist")
    void testGetAllTeamsEmpty() {
        // Arrange
        when(teamService.getAllTeams()).thenReturn([])

        // Act
        List<Team> result = teamController.getAllTeams()

        // Assert
        assertNotNull(result)
        assertTrue(result.isEmpty())
        
        verify(teamService, times(1)).getAllTeams()
    }

    @Test
    @DisplayName("Get all teams should return empty array via MockMvc when no teams")
    void testGetAllTeamsEmptyWithMockMvc() throws Exception {
        // Arrange
        when(teamService.getAllTeams()).thenReturn([])

        // Act & Assert
        mockMvc.perform(get("/api/teams/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isArray())
                .andExpect(jsonPath('$.length()').value(0))
        
        verify(teamService, times(1)).getAllTeams()
    }

    @Test
    @DisplayName("Get all teams should return correct team properties")
    void testGetAllTeamsWithCorrectProperties() {
        // Arrange
        when(teamService.getAllTeams()).thenReturn(sampleTeams)

        // Act
        List<Team> result = teamController.getAllTeams()

        // Assert
        Team firstTeam = result.get(0)
        assertEquals(101L, firstTeam.getTeamId())
        assertEquals("Lakers", firstTeam.getTeamName())
        assertEquals("Los Angeles", firstTeam.getTeamCity())
        assertEquals("LAL", firstTeam.getTeamTricode())
        assertEquals("lakers", firstTeam.getTeamSlug())
        assertEquals(45, firstTeam.getWins())
        assertEquals(37, firstTeam.getLosses())
    }

    @Test
    @DisplayName("Get all teams endpoint should have CORS enabled")
    void testCorsConfiguration() throws Exception {
        // Arrange
        when(teamService.getAllTeams()).thenReturn(sampleTeams)

        // Act & Assert
        mockMvc.perform(get("/api/teams/all")
                .header("Origin", "http://localhost:3000"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Access-Control-Allow-Origin"))
    }

    @Test
    @DisplayName("Get all teams should handle single team")
    void testGetAllTeamsWithSingleTeam() {
        // Arrange
        List<Team> singleTeam = [sampleTeams.get(0)]
        when(teamService.getAllTeams()).thenReturn(singleTeam)

        // Act
        List<Team> result = teamController.getAllTeams()

        // Assert
        assertNotNull(result)
        assertEquals(1, result.size())
        assertEquals("Lakers", result.get(0).getTeamName())
    }

    @Test
    @DisplayName("Controller should call service exactly once")
    void testServiceCalledOnce() {
        // Arrange
        when(teamService.getAllTeams()).thenReturn(sampleTeams)

        // Act
        teamController.getAllTeams()

        // Assert
        verify(teamService, times(1)).getAllTeams()
        verifyNoMoreInteractions(teamService)
    }

    @Test
    @DisplayName("Get all teams should return teams in correct order")
    void testGetAllTeamsOrder() {
        // Arrange
        when(teamService.getAllTeams()).thenReturn(sampleTeams)

        // Act
        List<Team> result = teamController.getAllTeams()

        // Assert
        assertEquals(101L, result.get(0).getTeamId())
        assertEquals(102L, result.get(1).getTeamId())
        assertEquals(103L, result.get(2).getTeamId())
    }

    @Test
    @DisplayName("Get all teams should include all team statistics")
    void testGetAllTeamsIncludesStatistics() throws Exception {
        // Arrange
        when(teamService.getAllTeams()).thenReturn(sampleTeams)

        // Act & Assert
        mockMvc.perform(get("/api/teams/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$[0].wins').value(45))
                .andExpect(jsonPath('$[0].losses').value(37))
                .andExpect(jsonPath('$[0].seed').value(7))
                .andExpect(jsonPath('$[1].wins').value(53))
                .andExpect(jsonPath('$[2].wins').value(57))
    }
}
