package com.example.demo.config

import com.example.demo.security.JwtAuthenticationFilter
import com.example.demo.security.JwtTokenProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Arrays

@Configuration
@EnableWebSecurity
class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class)

    @Value('${APP_BASE_URL:http://localhost:8080}')
    private String baseUrl

    @Value('${frontend.url:exp://localhost:8081}')
    private String frontendUrl
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter
    
    @Autowired
    private JwtTokenProvider tokenProvider

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors { cors ->
                cors.configurationSource(corsConfigurationSource())
            }
            .authorizeHttpRequests { authz ->
                authz
                    // Public endpoints
                    .requestMatchers("/", "/home", "/public/**", "/error", "/h2-console/**", "/test").permitAll()
                    .requestMatchers("/api/public/**").permitAll()
                    .requestMatchers("/login", "/login/**", "/oauth2/**", "/login/oauth2/**").permitAll()
                    // Protected endpoints
                    .requestMatchers("/api/auth/token").authenticated()
                    .requestMatchers("/dashboard", "/profile").authenticated()
                    .requestMatchers("/api/games/**", "/api/teams/**").authenticated()
                    .requestMatchers("/api/user").authenticated()
                    // All other requests require authentication
                    .anyRequest().authenticated()
            }
            .oauth2Login { oauth2 ->
                oauth2
                    .loginPage("/login")
                    .successHandler(oauthSuccessHandler())
                    .failureHandler { request, response, exception ->
                        def logger = LoggerFactory.getLogger(SecurityConfig.class)
                        logger.error("❌ OAuth2 login failed: {}", exception.getMessage(), exception)
                        response.sendRedirect("/login?error=true")
                    }
            }
            .logout { logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/?logout=success")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .permitAll()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf { csrf ->
                csrf.disable()
            }
            .headers { headers ->
                headers.frameOptions().disable()
            }

        return http.build()
    }

    @Bean
    AuthenticationSuccessHandler oauthSuccessHandler() {
        // Capture dependencies in local variables for closure access
        final String redirectBaseUrl = frontendUrl
        final JwtTokenProvider jwtProvider = tokenProvider
        
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                try {
                    OAuth2User principal = (OAuth2User) authentication.getPrincipal()
                    
                    // Extract user info
                    String name = principal.getAttribute("name") ?: principal.getAttribute("login") ?: "User"
                    String email = principal.getAttribute("email") ?: ""
                    String avatar = principal.getAttribute("avatar_url") ?: principal.getAttribute("picture") ?: ""
                    
                    log.info("✅ OAuth login successful for user: {}", name)
                    log.info("🔄 Frontend URL configured: {}", redirectBaseUrl)
                    
                    // Check if frontendUrl is configured (mobile app)
                    if (redirectBaseUrl == null || redirectBaseUrl.isEmpty() || !redirectBaseUrl.startsWith("exp://")) {
                        log.warn("⚠️ FRONTEND_URL not configured or not mobile, falling back to /dashboard")
                        return "/dashboard"
                    }
                    
                    // Generate JWT token for mobile
                    String username = email ?: name
                    String token = jwtProvider.generateToken(username)
                    log.info("🔑 Generated JWT token for mobile app")
                    
                    // Build redirect URL with user info AND token as query parameters
                    String redirectUrl = redirectBaseUrl + 
                        "?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8.toString()) +
                        "&name=" + URLEncoder.encode(name, StandardCharsets.UTF_8.toString()) +
                        "&email=" + URLEncoder.encode(email, StandardCharsets.UTF_8.toString()) +
                        "&avatar=" + URLEncoder.encode(avatar, StandardCharsets.UTF_8.toString()) +
                        "&authenticated=true"
                    
                    log.info("✅ Redirect URL with token: {}", redirectUrl.substring(0, Math.min(100, redirectUrl.length())))
                    return redirectUrl
                } catch (Exception e) {
                    log.error("❌ Error in OAuth success handler: {}", e.getMessage(), e)
                    return "/dashboard"
                }
            }
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration()
        
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:*",
            "https://*.ngrok.io",
            "https://*.herokuapp.com",
            "https://*.railway.app",
            "https://*.render.com",
            baseUrl
        ))
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"))
        configuration.setAllowedHeaders(Arrays.asList("*"))
        configuration.setAllowCredentials(true)
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}