package com.example.demo.controller

import com.example.demo.controller.StatusController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import static org.junit.jupiter.api.Assertions.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(StatusController.class)
@ActiveProfiles("test")
@DisplayName("StatusController Tests")
class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private StatusController statusController

    @Test
    @DisplayName("Health check should return status map with UP status")
    void testHealthCheck() {
        // Act
        Map<String, Object> result = statusController.healthCheck()

        // Assert
        assertNotNull(result)
        assertEquals("UP", result.get("status"))
        assertEquals("Application is running", result.get("message"))
        assertTrue(result.containsKey("timestamp"))
        assertEquals("DISABLED - JawsDB quota exhausted", result.get("database"))
    }

    @Test
    @DisplayName("Health check should include all required fields")
    void testHealthCheckContainsAllFields() {
        // Act
        Map<String, Object> result = statusController.healthCheck()

        // Assert
        assertTrue(result.containsKey("status"))
        assertTrue(result.containsKey("message"))
        assertTrue(result.containsKey("timestamp"))
        assertTrue(result.containsKey("database"))
    }

    @Test
    @DisplayName("Health check should return valid timestamp")
    void testHealthCheckTimestamp() {
        // Act
        Map<String, Object> result = statusController.healthCheck()

        // Assert
        Object timestamp = result.get("timestamp")
        assertNotNull(timestamp)
        assertTrue(timestamp instanceof java.util.Date)
    }

    @Test
    @DisplayName("Home endpoint should return API running message")
    void testHomeEndpoint() {
        // Act
        String result = statusController.home()

        // Assert
        assertNotNull(result)
        assertTrue(result.contains("Jump Ball API"))
        assertTrue(result.contains("running"))
        assertTrue(result.contains("Database"))
    }

    @Test
    @DisplayName("Health check via MockMvc should return 200 OK")
    void testHealthCheckWithMockMvc() throws Exception {
        mockMvc.perform(get("/api/status/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.status').value("UP"))
                .andExpect(jsonPath('$.message').value("Application is running"))
                .andExpect(jsonPath('$.database').value("DISABLED - JawsDB quota exhausted"))
                .andExpect(jsonPath('$.timestamp').exists())
    }

    @Test
    @DisplayName("Home endpoint via MockMvc should return 200 OK with message")
    void testHomeEndpointWithMockMvc() throws Exception {
        mockMvc.perform(get("/api/status/"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Jump Ball API")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("running")))
    }

    @Test
    @DisplayName("Status endpoint should have CORS enabled")
    void testCorsConfiguration() throws Exception {
        mockMvc.perform(get("/api/status/health")
                .header("Origin", "http://localhost:3000"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Access-Control-Allow-Origin"))
    }

    @Test
    @DisplayName("Health check should be consistent across multiple calls")
    void testHealthCheckConsistency() {
        // Act
        Map<String, Object> result1 = statusController.healthCheck()
        Map<String, Object> result2 = statusController.healthCheck()

        // Assert
        assertEquals(result1.get("status"), result2.get("status"))
        assertEquals(result1.get("message"), result2.get("message"))
        assertEquals(result1.get("database"), result2.get("database"))
    }

    @Test
    @DisplayName("Health check status should always be UP")
    void testHealthCheckStatusAlwaysUp() {
        // Act
        Map<String, Object> result = statusController.healthCheck()

        // Assert
        assertEquals("UP", result.get("status"))
        assertNotEquals("DOWN", result.get("status"))
    }

    @Test
    @DisplayName("Health check should indicate database is disabled")
    void testHealthCheckDatabaseStatus() {
        // Act
        Map<String, Object> result = statusController.healthCheck()

        // Assert
        String databaseStatus = (String) result.get("database")
        assertTrue(databaseStatus.contains("DISABLED"))
        assertTrue(databaseStatus.contains("quota"))
    }

    @Test
    @DisplayName("Home message should mention database status")
    void testHomeMessageContainsDatabaseInfo() {
        // Act
        String result = statusController.home()

        // Assert
        assertTrue(result.toLowerCase().contains("database"))
        assertTrue(result.toLowerCase().contains("disabled") || result.toLowerCase().contains("temporarily"))
    }

    @Test
    @DisplayName("Health check endpoint should be accessible without authentication")
    void testHealthCheckPublicAccess() throws Exception {
        // This test verifies the endpoint is accessible without auth
        mockMvc.perform(get("/api/status/health"))
                .andExpect(status().isOk())
    }

    @Test
    @DisplayName("Status endpoints should return JSON for health check")
    void testHealthCheckReturnsJson() throws Exception {
        mockMvc.perform(get("/api/status/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
    }

    @Test
    @DisplayName("Home endpoint should return plain text")
    void testHomeReturnsPlainText() throws Exception {
        mockMvc.perform(get("/api/status/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
    }
}
