package com.example.demo.model

import com.example.demo.model.Team
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import static org.junit.jupiter.api.Assertions.*

@DisplayName("Team Model Tests")
class TeamTest {

    private Team team

    @BeforeEach
    void setUp() {
        team = new Team()
    }

    @Test
    @DisplayName("Default constructor should create empty Team")
    void testDefaultConstructor() {
        Team newTeam = new Team()
        assertNotNull(newTeam)
        assertNull(newTeam.getTeamId())
        assertNull(newTeam.getTeamName())
        assertNull(newTeam.getTeamCity())
        assertNull(newTeam.getTeamTricode())
        assertNull(newTeam.getTeamSlug())
    }

    @Test
    @DisplayName("Parameterized constructor should set basic fields")
    void testParameterizedConstructor() {
        Long teamId = 101L
        String teamName = "Test Team"
        String teamCity = "Test City"
        String teamTricode = "TST"
        String teamSlug = "test-team"

        Team newTeam = new Team(teamId, teamName, teamCity, teamTricode, teamSlug)

        assertEquals(teamId, newTeam.getTeamId())
        assertEquals(teamName, newTeam.getTeamName())
        assertEquals(teamCity, newTeam.getTeamCity())
        assertEquals(teamTricode, newTeam.getTeamTricode())
        assertEquals(teamSlug, newTeam.getTeamSlug())
    }

    @Test
    @DisplayName("Team ID setter and getter should work correctly")
    void testTeamIdSetterGetter() {
        Long teamId = 101L
        team.setTeamId(teamId)
        assertEquals(teamId, team.getTeamId())
    }

    @Test
    @DisplayName("Team name setter and getter should work correctly")
    void testTeamNameSetterGetter() {
        String teamName = "Lakers"
        team.setTeamName(teamName)
        assertEquals(teamName, team.getTeamName())
    }

    @Test
    @DisplayName("Team city setter and getter should work correctly")
    void testTeamCitySetterGetter() {
        String teamCity = "Los Angeles"
        team.setTeamCity(teamCity)
        assertEquals(teamCity, team.getTeamCity())
    }

    @Test
    @DisplayName("Team tricode setter and getter should work correctly")
    void testTeamTricodeSetterGetter() {
        String tricode = "LAL"
        team.setTeamTricode(tricode)
        assertEquals(tricode, team.getTeamTricode())
    }

    @Test
    @DisplayName("Team slug setter and getter should work correctly")
    void testTeamSlugSetterGetter() {
        String slug = "lakers"
        team.setTeamSlug(slug)
        assertEquals(slug, team.getTeamSlug())
    }

    @Test
    @DisplayName("Wins and losses should work correctly")
    void testWinsAndLosses() {
        Integer wins = 45
        Integer losses = 37

        team.setWins(wins)
        team.setLosses(losses)

        assertEquals(wins, team.getWins())
        assertEquals(losses, team.getLosses())
    }

    @Test
    @DisplayName("Score setter and getter should work correctly")
    void testScoreSetterGetter() {
        Integer score = 110
        team.setScore(score)
        assertEquals(score, team.getScore())
    }

    @Test
    @DisplayName("Seed setter and getter should work correctly")
    void testSeedSetterGetter() {
        Integer seed = 7
        team.setSeed(seed)
        assertEquals(seed, team.getSeed())
    }

    @Test
    @DisplayName("Equals method should work correctly")
    void testEquals() {
        // Test same object
        assertTrue(team.equals(team))

        // Test null
        assertFalse(team.equals(null))

        // Test different class
        assertFalse(team.equals("not a team"))

        // Test teams with same ID
        Team team1 = new Team()
        Team team2 = new Team()
        Long teamId = 101L

        team1.setTeamId(teamId)
        team2.setTeamId(teamId)
        assertTrue(team1.equals(team2))

        // Test teams with different IDs
        team2.setTeamId(102L)
        assertFalse(team1.equals(team2))

        // Test team with null ID
        team1.setTeamId(null)
        team2.setTeamId(null)
        assertFalse(team1.equals(team2))
    }

    @Test
    @DisplayName("HashCode should be consistent")
    void testHashCode() {
        Team team1 = new Team()
        Team team2 = new Team()

        // Same class should have same hashcode
        assertEquals(team1.hashCode(), team2.hashCode())

        // Adding same ID should still be equal
        Long teamId = 101L
        team1.setTeamId(teamId)
        team2.setTeamId(teamId)
        assertEquals(team1.hashCode(), team2.hashCode())
    }

    @Test
    @DisplayName("ToString should return meaningful string representation")
    void testToString() {
        team.setTeamId(101L)
        team.setTeamName("Lakers")
        team.setTeamCity("Los Angeles")
        team.setTeamTricode("LAL")
        team.setTeamSlug("lakers")
        team.setWins(45)
        team.setLosses(37)

        String result = team.toString()
        assertNotNull(result)
        assertTrue(result.contains("101"))
        assertTrue(result.contains("Lakers"))
        assertTrue(result.contains("Los Angeles"))
        assertTrue(result.contains("LAL"))
        assertTrue(result.contains("lakers"))
        assertTrue(result.contains("45"))
        assertTrue(result.contains("37"))
    }

    @Test
    @DisplayName("Team with all fields set should maintain values")
    void testCompleteTeamSetup() {
        Long teamId = 101L
        String teamName = "Complete Team"
        String teamCity = "Complete City"
        String teamTricode = "COM"
        String teamSlug = "complete-team"
        String nickname = "Completers"
        String logo = "http://example.com/logo.png"
        Integer wins = 50
        Integer losses = 32
        Integer score = 115
        Integer seed = 3

        team.setTeamId(teamId)
        team.setTeamName(teamName)
        team.setTeamCity(teamCity)
        team.setTeamTricode(teamTricode)
        team.setTeamSlug(teamSlug)
        team.setNickname(nickname)
        team.setLogo(logo)
        team.setWins(wins)
        team.setLosses(losses)
        team.setScore(score)
        team.setSeed(seed)

        // Verify all fields
        assertEquals(teamId, team.getTeamId())
        assertEquals(teamName, team.getTeamName())
        assertEquals(teamCity, team.getTeamCity())
        assertEquals(teamTricode, team.getTeamTricode())
        assertEquals(teamSlug, team.getTeamSlug())
        assertEquals(nickname, team.getNickname())
        assertEquals(logo, team.getLogo())
        assertEquals(wins, team.getWins())
        assertEquals(losses, team.getLosses())
        assertEquals(score, team.getScore())
        assertEquals(seed, team.getSeed())
    }
}