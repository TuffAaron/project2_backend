package com.example.demo.controller

import com.example.demo.controller.AuthController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.ui.Model
import static org.junit.jupiter.api.Assertions.*
import static org.mockito.Mockito.*
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(AuthController.class)
@ActiveProfiles("test")
@DisplayName("AuthController Tests")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository

    @Autowired
    private AuthController authController

    private OAuth2User mockGitHubUser
    private OAuth2User mockGoogleUser
    private Model mockModel

    @BeforeEach
    void setUp() {
        mockModel = mock(Model.class)
        
        // Create mock GitHub user
        Map<String, Object> githubAttributes = new HashMap<>()
        githubAttributes.put("login", "testuser")
        githubAttributes.put("name", "Test User")
        githubAttributes.put("email", "test@github.com")
        githubAttributes.put("avatar_url", "https://github.com/avatar.png")
        
        mockGitHubUser = new DefaultOAuth2User(
            [new SimpleGrantedAuthority("ROLE_USER")],
            githubAttributes,
            "login"
        )
        
        // Create mock Google user
        Map<String, Object> googleAttributes = new HashMap<>()
        googleAttributes.put("name", "Google User")
        googleAttributes.put("email", "test@google.com")
        googleAttributes.put("picture", "https://google.com/picture.png")
        
        mockGoogleUser = new DefaultOAuth2User(
            [new SimpleGrantedAuthority("ROLE_USER")],
            googleAttributes,
            "name"
        )
    }

    @Test
    @DisplayName("Home endpoint should return home view")
    void testHomeEndpoint() {
        // Act
        String result = authController.home()

        // Assert
        assertEquals("home", result)
    }

    @Test
    @DisplayName("Test endpoint should return success message")
    void testTestEndpoint() {
        // Act
        String result = authController.test()

        // Assert
        assertEquals("App is running! Troubleshoot branch deployed.", result)
    }

    @Test
    @DisplayName("Login endpoint should return login view")
    void testLoginEndpoint() {
        // Act
        String result = authController.login()

        // Assert
        assertEquals("login", result)
    }

    @Test
    @DisplayName("Dashboard with GitHub user should populate model correctly")
    void testDashboardWithGitHubUser() {
        // Act
        String result = authController.dashboard(mockModel, mockGitHubUser)

        // Assert
        assertEquals("dashboard", result)
        verify(mockModel).addAttribute("name", "Test User")
        verify(mockModel).addAttribute("email", "test@github.com")
        verify(mockModel).addAttribute("avatar", "https://github.com/avatar.png")
        verify(mockModel).addAttribute("provider", "GitHub")
    }

    @Test
    @DisplayName("Dashboard with Google user should populate model correctly")
    void testDashboardWithGoogleUser() {
        // Act
        String result = authController.dashboard(mockModel, mockGoogleUser)

        // Assert
        assertEquals("dashboard", result)
        verify(mockModel).addAttribute("name", "Google User")
        verify(mockModel).addAttribute("email", "test@google.com")
        verify(mockModel).addAttribute("avatar", "https://google.com/picture.png")
        verify(mockModel).addAttribute("provider", "Google")
    }

    @Test
    @DisplayName("Dashboard without authentication should redirect to login")
    void testDashboardWithoutAuthentication() {
        // Act
        String result = authController.dashboard(mockModel, null)

        // Assert
        assertEquals("redirect:/login", result)
        verify(mockModel, never()).addAttribute(anyString(), any())
    }

    @Test
    @DisplayName("Profile with authenticated user should return profile view")
    void testProfileWithAuthentication() {
        // Act
        String result = authController.profile(mockModel, mockGitHubUser)

        // Assert
        assertEquals("profile", result)
        verify(mockModel).addAttribute("user", mockGitHubUser.getAttributes())
        verify(mockModel).addAttribute("provider", "GitHub")
    }

    @Test
    @DisplayName("Profile without authentication should still return profile view")
    void testProfileWithoutAuthentication() {
        // Act
        String result = authController.profile(mockModel, null)

        // Assert
        assertEquals("profile", result)
        verify(mockModel, never()).addAttribute(anyString(), any())
    }

    @Test
    @DisplayName("Home endpoint via MockMvc should return 200 and home view")
    void testHomeEndpointWithMockMvc() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
    }

    @Test
    @DisplayName("Test endpoint via MockMvc should return success message")
    void testTestEndpointWithMockMvc() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("App is running! Troubleshoot branch deployed."))
    }

    @Test
    @DisplayName("Login endpoint via MockMvc should return login view")
    void testLoginEndpointWithMockMvc() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
    }

    @Test
    @DisplayName("Dashboard with OAuth2 login should return dashboard view")
    void testDashboardWithMockMvcAndAuth() throws Exception {
        mockMvc.perform(get("/dashboard")
                .with(oauth2Login()
                    .attributes(attrs -> {
                        attrs.put("login", "testuser")
                        attrs.put("name", "Test User")
                        attrs.put("email", "test@github.com")
                        attrs.put("avatar_url", "https://github.com/avatar.png")
                    })))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attributeExists("email"))
                .andExpect(model().attributeExists("provider"))
    }

    @Test
    @DisplayName("Dashboard handles missing email gracefully")
    void testDashboardWithMissingEmail() {
        // Arrange
        Map<String, Object> attrs = new HashMap<>()
        attrs.put("login", "testuser")
        attrs.put("name", "Test User")
        // No email
        
        OAuth2User userWithoutEmail = new DefaultOAuth2User(
            [new SimpleGrantedAuthority("ROLE_USER")],
            attrs,
            "login"
        )

        // Act
        String result = authController.dashboard(mockModel, userWithoutEmail)

        // Assert
        assertEquals("dashboard", result)
        verify(mockModel).addAttribute("name", "Test User")
        verify(mockModel).addAttribute("email", "No email")
    }

    @Test
    @DisplayName("Dashboard handles missing name with fallback to login")
    void testDashboardWithMissingName() {
        // Arrange
        Map<String, Object> attrs = new HashMap<>()
        attrs.put("login", "testuser")
        attrs.put("email", "test@example.com")
        // No name
        
        OAuth2User userWithoutName = new DefaultOAuth2User(
            [new SimpleGrantedAuthority("ROLE_USER")],
            attrs,
            "login"
        )

        // Act
        String result = authController.dashboard(mockModel, userWithoutName)

        // Assert
        assertEquals("dashboard", result)
        verify(mockModel).addAttribute("name", "testuser")
    }

    @Test
    @DisplayName("Dashboard handles completely missing identifiers")
    void testDashboardWithNoIdentifiers() {
        // Arrange
        Map<String, Object> attrs = new HashMap<>()
        attrs.put("id", "12345")
        // No name, login, or email
        
        OAuth2User minimalUser = new DefaultOAuth2User(
            [new SimpleGrantedAuthority("ROLE_USER")],
            attrs,
            "id"
        )

        // Act
        String result = authController.dashboard(mockModel, minimalUser)

        // Assert
        assertEquals("dashboard", result)
        verify(mockModel).addAttribute("name", "User")
        verify(mockModel).addAttribute("email", "No email")
    }
}
