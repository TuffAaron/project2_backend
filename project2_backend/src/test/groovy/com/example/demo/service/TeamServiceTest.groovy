package com.example.demo.service

import com.example.demo.service.TeamService
import com.example.demo.model.Team
import com.example.demo.repository.TeamRepository
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
@DisplayName("TeamService Tests")
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository

    @InjectMocks
    private TeamService teamService

    private List<Team> sampleTeams

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this)
        
        // Create sample teams for testing
        sampleTeams = []
        
        Team team1 = new Team(101L, "Lakers", "Los Angeles", "LAL", "lakers")
        team1.setWins(45)
        team1.setLosses(37)
        team1.setScore(110)
        team1.setSeed(7)
        team1.setNickname("Lakers")
        team1.setLogo("https://cdn.nba.com/logos/lakers.png")
        
        Team team2 = new Team(102L, "Warriors", "Golden State", "GSW", "warriors")
        team2.setWins(53)
        team2.setLosses(29)
        team2.setScore(115)
        team2.setSeed(3)
        team2.setNickname("Warriors")
        team2.setLogo("https://cdn.nba.com/logos/warriors.png")
        
        Team team3 = new Team(103L, "Celtics", "Boston", "BOS", "celtics")
        team3.setWins(57)
        team3.setLosses(25)
        team3.setScore(108)
        team3.setSeed(1)
        team3.setNickname("Celtics")
        team3.setLogo("https://cdn.nba.com/logos/celtics.png")
        
        sampleTeams.addAll([team1, team2, team3])
    }

    @Test
    @DisplayName("Get all teams should return all teams from repository")
    void testGetAllTeams() {
        // Arrange
        when(teamRepository.findAll()).thenReturn(sampleTeams)

        // Act
        List<Team> result = teamService.getAllTeams()

        // Assert
        assertNotNull(result)
        assertEquals(3, result.size())
        assertEquals("Lakers", result.get(0).getTeamName())
        assertEquals("Warriors", result.get(1).getTeamName())
        assertEquals("Celtics", result.get(2).getTeamName())
        
        verify(teamRepository, times(1)).findAll()
    }

    @Test
    @DisplayName("Get all teams should return empty list when no teams exist")
    void testGetAllTeamsEmpty() {
        // Arrange
        when(teamRepository.findAll()).thenReturn([])

        // Act
        List<Team> result = teamService.getAllTeams()

        // Assert
        assertNotNull(result)
        assertTrue(result.isEmpty())
        
        verify(teamRepository, times(1)).findAll()
    }

    @Test
    @DisplayName("Get all teams should return correct count")
    void testGetAllTeamsReturnsCorrectCount() {
        // Arrange
        when(teamRepository.findAll()).thenReturn(sampleTeams)

        // Act
        List<Team> result = teamService.getAllTeams()

        // Assert
        assertEquals(sampleTeams.size(), result.size())
        verify(teamRepository, times(1)).findAll()
    }

    @Test
    @DisplayName("Get all teams should contain teams with correct properties")
    void testGetAllTeamsHaveCorrectProperties() {
        // Arrange
        when(teamRepository.findAll()).thenReturn(sampleTeams)

        // Act
        List<Team> result = teamService.getAllTeams()

        // Assert
        Team lakers = result.get(0)
        assertEquals(101L, lakers.getTeamId())
        assertEquals("Lakers", lakers.getTeamName())
        assertEquals("Los Angeles", lakers.getTeamCity())
        assertEquals("LAL", lakers.getTeamTricode())
        assertEquals("lakers", lakers.getTeamSlug())
        assertEquals(45, lakers.getWins())
        assertEquals(37, lakers.getLosses())
        assertEquals(7, lakers.getSeed())
    }

    @Test
    @DisplayName("Get all teams should maintain team order from repository")
    void testGetAllTeamsMaintainsOrder() {
        // Arrange
        when(teamRepository.findAll()).thenReturn(sampleTeams)

        // Act
        List<Team> result = teamService.getAllTeams()

        // Assert
        for (int i = 0; i < sampleTeams.size(); i++) {
            assertEquals(sampleTeams.get(i).getTeamId(), result.get(i).getTeamId())
        }
    }

    @Test
    @DisplayName("Get all teams should handle single team")
    void testGetAllTeamsWithSingleTeam() {
        // Arrange
        List<Team> singleTeam = [sampleTeams.get(0)]
        when(teamRepository.findAll()).thenReturn(singleTeam)

        // Act
        List<Team> result = teamService.getAllTeams()

        // Assert
        assertNotNull(result)
        assertEquals(1, result.size())
        assertEquals("Lakers", result.get(0).getTeamName())
    }

    @Test
    @DisplayName("Repository should be called exactly once per getAllTeams call")
    void testRepositoryCalledOnce() {
        // Arrange
        when(teamRepository.findAll()).thenReturn(sampleTeams)

        // Act
        teamService.getAllTeams()

        // Assert
        verify(teamRepository, times(1)).findAll()
        verifyNoMoreInteractions(teamRepository)
    }
}
