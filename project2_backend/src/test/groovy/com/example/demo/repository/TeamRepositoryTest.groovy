package com.example.demo.repository

import com.example.demo.model.Team
import com.example.demo.repository.TeamRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import static org.junit.jupiter.api.Assertions.*

@DataJpaTest
@DisplayName("TeamRepository Integration Tests")
class TeamRepositoryTest {

    @Autowired
    private TestEntityManager entityManager

    @Autowired
    private TeamRepository teamRepository

    private Team team1, team2, team3

    @BeforeEach
    void setUp() {
        // Create test teams
        team1 = new Team(101L, "Lakers", "Los Angeles", "LAL", "lakers")
        team1.setWins(45)
        team1.setLosses(37)
        team1.setScore(110)
        team1.setSeed(7)

        team2 = new Team(102L, "Warriors", "Golden State", "GSW", "warriors")
        team2.setWins(53)
        team2.setLosses(29)
        team2.setScore(115)
        team2.setSeed(3)

        team3 = new Team(103L, "Celtics", "Boston", "BOS", "celtics")
        team3.setWins(57)
        team3.setLosses(25)
        team3.setScore(108)
        team3.setSeed(1)

        // Persist test data
        entityManager.persistAndFlush(team1)
        entityManager.persistAndFlush(team2)
        entityManager.persistAndFlush(team3)
    }

    @Test
    @DisplayName("Find all teams should return all persisted teams")
    void testFindAll() {
        // Act
        List<Team> teams = teamRepository.findAll()

        // Assert
        assertNotNull(teams)
        assertEquals(3, teams.size())
        
        List<String> teamNames = teams.collect { it.teamName }
        assertTrue(teamNames.contains("Lakers"))
        assertTrue(teamNames.contains("Warriors"))
        assertTrue(teamNames.contains("Celtics"))
    }

    @Test
    @DisplayName("Find by ID should return specific team")
    void testFindById() {
        // Act
        Optional<Team> teamOptional = teamRepository.findById(101L)

        // Assert
        assertTrue(teamOptional.isPresent())
        Team team = teamOptional.get()
        assertEquals(101L, team.teamId)
        assertEquals("Lakers", team.teamName)
        assertEquals("Los Angeles", team.teamCity)
        assertEquals("LAL", team.teamTricode)
        assertEquals("lakers", team.teamSlug)
    }

    @Test
    @DisplayName("Find by ID should return empty for non-existent team")
    void testFindById_NonExistent() {
        // Act
        Optional<Team> teamOptional = teamRepository.findById(999L)

        // Assert
        assertFalse(teamOptional.isPresent())
    }

    @Test
    @DisplayName("Save should persist new team")
    void testSave() {
        // Arrange
        Team newTeam = new Team(104L, "Nets", "Brooklyn", "BKN", "nets")
        newTeam.setWins(38)
        newTeam.setLosses(44)
        newTeam.setScore(105)
        newTeam.setSeed(10)

        // Act
        Team savedTeam = teamRepository.save(newTeam)

        // Assert
        assertNotNull(savedTeam)
        assertEquals(104L, savedTeam.teamId)
        
        // Verify it's in the database
        Optional<Team> foundTeam = teamRepository.findById(104L)
        assertTrue(foundTeam.isPresent())
        assertEquals("Nets", foundTeam.get().teamName)
        assertEquals("Brooklyn", foundTeam.get().teamCity)
    }

    @Test
    @DisplayName("Update should modify existing team")
    void testUpdate() {
        // Arrange
        Team existingTeam = teamRepository.findById(101L).get()
        existingTeam.setWins(46)
        existingTeam.setLosses(36)
        existingTeam.setScore(112)

        // Act
        Team updatedTeam = teamRepository.save(existingTeam)

        // Assert
        assertEquals(46, updatedTeam.wins)
        assertEquals(36, updatedTeam.losses)
        assertEquals(112, updatedTeam.score)
        
        // Verify in database
        Team dbTeam = teamRepository.findById(101L).get()
        assertEquals(46, dbTeam.wins)
        assertEquals(36, dbTeam.losses)
        assertEquals(112, dbTeam.score)
    }

    @Test
    @DisplayName("Delete should remove team from database")
    void testDelete() {
        // Arrange
        assertTrue(teamRepository.findById(101L).isPresent())

        // Act
        teamRepository.deleteById(101L)

        // Assert
        assertFalse(teamRepository.findById(101L).isPresent())
        assertEquals(2, teamRepository.findAll().size())
    }

    @Test
    @DisplayName("Count should return correct number of teams")
    void testCount() {
        // Act
        long count = teamRepository.count()

        // Assert
        assertEquals(3L, count)
    }

    @Test
    @DisplayName("Exists by ID should return true for existing team")
    void testExistsById() {
        // Act & Assert
        assertTrue(teamRepository.existsById(101L))
        assertTrue(teamRepository.existsById(102L))
        assertTrue(teamRepository.existsById(103L))
        assertFalse(teamRepository.existsById(999L))
    }

    @Test
    @DisplayName("Save all should persist multiple teams")
    void testSaveAll() {
        // Arrange
        Team team4 = new Team(104L, "Heat", "Miami", "MIA", "heat")
        Team team5 = new Team(105L, "Bulls", "Chicago", "CHI", "bulls")
        List<Team> newTeams = [team4, team5]

        // Act
        List<Team> savedTeams = teamRepository.saveAll(newTeams)

        // Assert
        assertEquals(2, savedTeams.size())
        assertEquals(5, teamRepository.count())
        
        assertTrue(teamRepository.existsById(104L))
        assertTrue(teamRepository.existsById(105L))
    }

    @Test
    @DisplayName("Find all by ID should return matching teams")
    void testFindAllById() {
        // Act
        List<Team> teams = teamRepository.findAllById([101L, 103L])

        // Assert
        assertEquals(2, teams.size())
        List<String> teamNames = teams.collect { it.teamName }
        assertTrue(teamNames.contains("Lakers"))
        assertTrue(teamNames.contains("Celtics"))
        assertFalse(teamNames.contains("Warriors"))
    }
}