package com.example.demo.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
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

    @Value('${APP_BASE_URL:http://localhost:8080}')
    private String baseUrl

    @Autowired(required = false)
    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors { cors ->
                cors.configurationSource(corsConfigurationSource())
            }
            .authorizeHttpRequests { authz ->
                authz
                    // Public endpoints
                    .requestMatchers("/", "/home", "/public/**", "/error", "/h2-console/**").permitAll()
                    .requestMatchers("/api/public/**").permitAll()
                    .requestMatchers("/login", "/oauth2/**").permitAll()
                    // Protected endpoints
                    .requestMatchers("/api/games/**", "/api/teams/**").authenticated()
                    .requestMatchers("/dashboard/**", "/profile/**").authenticated()
                    .requestMatchers("/api/user").authenticated()
                    // All other requests require authentication
                    .anyRequest().authenticated()
            }
            .oauth2Login { oauth2 ->
                oauth2
                    .loginPage("/login")
                    .successHandler(authenticationSuccessHandler())
                    .failureUrl("/login?error=true")
                    .tokenEndpoint { token ->
                        if (accessTokenResponseClient != null) {
                            token.accessTokenResponseClient(accessTokenResponseClient)
                        }
                    }
            }
            .logout { logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            }
            .csrf { csrf ->
                csrf.disable() // Disable for API endpoints and H2 console
            }
            .headers { headers ->
                headers.frameOptions().disable() // Allow H2 console iframe
            }

        return http.build()
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

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler()
        handler.setDefaultTargetUrl("/dashboard")
        handler.setAlwaysUseDefaultTargetUrl(true)
        return handler
    }
}