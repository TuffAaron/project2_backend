package com.example.demo.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.Arrays

@Configuration
@EnableWebSecurity
class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class)

    @Value('${APP_BASE_URL:http://localhost:8080}')
    private String baseUrl

    @Value('${frontend.url:http://localhost:3000}')
    private String frontendUrl

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
                    // Protected endpoints - only /dashboard not /dashboard/**
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
            .csrf { csrf ->
                csrf.disable() // Temporarily disable to troubleshoot OAuth
            }
            .headers { headers ->
                headers.frameOptions().disable() // Allow H2 console iframe
            }

        return http.build()
    }

    @Bean
    AuthenticationSuccessHandler oauthSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                OAuth2User principal = (OAuth2User) authentication.getPrincipal()
                
                // Extract user info
                String name = principal.getAttribute("name") ?: principal.getAttribute("login") ?: "User"
                String email = principal.getAttribute("email") ?: ""
                String avatar = principal.getAttribute("avatar_url") ?: principal.getAttribute("picture") ?: ""
                
                log.info("✅ OAuth login successful for user: {}", name)
                log.info("🔄 Redirecting to frontend: {}", frontendUrl)
                
                // Build redirect URL with user info as query parameters
                String redirectUrl = frontendUrl + 
                    "?name=" + URLEncoder.encode(name, "UTF-8") +
                    "&email=" + URLEncoder.encode(email, "UTF-8") +
                    "&avatar=" + URLEncoder.encode(avatar, "UTF-8") +
                    "&authenticated=true"
                
                return redirectUrl
            }
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration()
        
        // Allow specific origins (including your ngrok/production URLs)
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:*",
            "https://*.ngrok.io",
            "https://*.herokuapp.com",
            "https://*.railway.app",
            "https://*.render.com",
            baseUrl
        ))
        
        // Allow common HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"))
        
        // Allow common headers
        configuration.setAllowedHeaders(Arrays.asList("*"))
        
        // Allow credentials for OAuth2
        configuration.setAllowCredentials(true)
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
