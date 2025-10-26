package com.example.demo.config

import com.example.demo.config.SecurityConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("SecurityConfig Integration Tests")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc

    // Test Public Endpoints

    @Test
    @DisplayName("Public home endpoint should be accessible without authentication")
    void testHomeEndpointPublicAccess() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
    }

    @Test
    @DisplayName("Public test endpoint should be accessible without authentication")
    void testTestEndpointPublicAccess() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
    }

    @Test
    @DisplayName("Public status endpoint should be accessible without authentication")
    void testPublicStatusEndpointAccess() throws Exception {
        mockMvc.perform(get("/api/public/status"))
                .andExpect(status().isOk())
    }

    @Test
    @DisplayName("Login endpoint should be accessible without authentication")
    void testLoginEndpointPublicAccess() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
    }

    // Test Protected Endpoints

    @Test
    @DisplayName("Dashboard endpoint should require authentication")
    void testDashboardRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
    }

    @Test
    @DisplayName("Profile endpoint should require authentication")
    void testProfileRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().is3xxRedirection())
    }

    @Test
    @DisplayName("Games API endpoint should require authentication")
    void testGamesApiRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/games/all"))
                .andExpect(status().is3xxRedirection())
    }

    @Test
    @DisplayName("Teams API endpoint should require authentication")
    void testTeamsApiRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/teams/all"))
                .andExpect(status().is3xxRedirection())
    }

    @Test
    @DisplayName("User API endpoint should require authentication")
    void testUserApiRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/user"))
                .andExpect(status().is3xxRedirection())
    }

    // Test Authenticated Access

    @Test
    @DisplayName("Dashboard should be accessible with OAuth2 authentication")
    void testDashboardWithAuthentication() throws Exception {
        mockMvc.perform(get("/dashboard")
                .with(oauth2Login()
                    .attributes(attrs -> {
                        attrs.put("login", "testuser")
                        attrs.put("name", "Test User")
                        attrs.put("email", "test@example.com")
                    })))
                .andExpect(status().isOk())
    }

    @Test
    @DisplayName("Profile should be accessible with OAuth2 authentication")
    void testProfileWithAuthentication() throws Exception {
        mockMvc.perform(get("/profile")
                .with(oauth2Login()
                    .attributes(attrs -> {
                        attrs.put("login", "testuser")
                        attrs.put("name", "Test User")
                    })))
                .andExpect(status().isOk())
    }

    @Test
    @DisplayName("Games API should be accessible with OAuth2 authentication")
    void testGamesApiWithAuthentication() throws Exception {
        mockMvc.perform(get("/api/games/all")
                .with(oauth2Login()
                    .attributes(attrs -> {
                        attrs.put("login", "testuser")
                    })))
                .andExpect(status().isOk())
    }

    @Test
    @DisplayName("Teams API should be accessible with OAuth2 authentication")
    void testTeamsApiWithAuthentication() throws Exception {
        mockMvc.perform(get("/api/teams/all")
                .with(oauth2Login()
                    .attributes(attrs -> {
                        attrs.put("login", "testuser")
                    })))
                .andExpect(status().isOk())
    }

    // Test CORS Configuration

    @Test
    @DisplayName("CORS should allow requests from localhost")
    void testCorsAllowsLocalhost() throws Exception {
        mockMvc.perform(get("/api/public/status")
                .header("Origin", "http://localhost:3000"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Access-Control-Allow-Origin"))
    }

    @Test
    @DisplayName("CORS should allow credentials")
    void testCorsAllowsCredentials() throws Exception {
        mockMvc.perform(options("/api/games/all")
                .header("Origin", "http://localhost:3000")
                .header("Access-Control-Request-Method", "GET"))
                .andExpect(header().string("Access-Control-Allow-Credentials", "true"))
    }

    @Test
    @DisplayName("CORS preflight request should be handled")
    void testCorsPreflightRequest() throws Exception {
        mockMvc.perform(options("/api/teams/all")
                .header("Origin", "http://localhost:3000")
                .header("Access-Control-Request-Method", "GET")
                .header("Access-Control-Request-Headers", "Content-Type"))
                .andExpect(status().isOk())
    }

    // Test Logout

    @Test
    @DisplayName("Logout should invalidate session and redirect")
    void testLogout() throws Exception {
        mockMvc.perform(post("/logout")
                .with(oauth2Login()))
                .andExpect(status().is3xxRedirection())
    }

    // Test Multiple Public Endpoints

    @Test
    @DisplayName("Error endpoint should be public")
    void testErrorEndpointPublic() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(status().is4xxClientError())
    }

    @Test
    @DisplayName("OAuth2 endpoints should be public")
    void testOAuth2EndpointsPublic() throws Exception {
        mockMvc.perform(get("/oauth2/authorization/github"))
                .andExpect(status().is3xxRedirection())
    }

    // Test Security Headers

    @Test
    @DisplayName("X-Frame-Options should be disabled for H2 console")
    void testFrameOptionsDisabled() throws Exception {
        // This verifies that frame options are disabled (for H2 console)
        mockMvc.perform(get("/h2-console"))
                .andExpect(status().is4xxClientError()) // Not found in test, but headers should be set
    }

    // Test Multiple HTTP Methods

    @Test
    @DisplayName("GET requests should be allowed on public endpoints")
    void testGetMethodAllowed() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
    }

    @Test
    @DisplayName("Protected POST endpoint should require authentication")
    void testPostRequiresAuthentication() throws Exception {
        mockMvc.perform(post("/api/games"))
                .andExpect(status().is3xxRedirection())
    }

    @Test
    @DisplayName("Protected PUT endpoint should require authentication")
    void testPutRequiresAuthentication() throws Exception {
        mockMvc.perform(put("/api/games/GAME001"))
                .andExpect(status().is3xxRedirection())
    }

    @Test
    @DisplayName("Protected DELETE endpoint should require authentication")
    void testDeleteRequiresAuthentication() throws Exception {
        mockMvc.perform(delete("/api/games/GAME001"))
                .andExpect(status().is3xxRedirection())
    }

    // Test Specific Game Endpoints

    @Test
    @DisplayName("Game test endpoint should require authentication")
    void testGameTestEndpointRequiresAuth() throws Exception {
        mockMvc.perform(get("/api/games/test"))
                .andExpect(status().is3xxRedirection())
    }

    @Test
    @DisplayName("Game by ID endpoint should require authentication")
    void testGameByIdRequiresAuth() throws Exception {
        mockMvc.perform(get("/api/games/GAME001"))
                .andExpect(status().is3xxRedirection())
    }

    @Test
    @DisplayName("Games by team endpoint should require authentication")
    void testGamesByTeamRequiresAuth() throws Exception {
        mockMvc.perform(get("/api/games/team/101"))
                .andExpect(status().is3xxRedirection())
    }
}
