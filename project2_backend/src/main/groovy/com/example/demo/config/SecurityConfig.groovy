package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests { authz ->
                authz
                    // Public endpoints
                    .requestMatchers("/", "/home", "/public/**", "/error", "/h2-console/**").permitAll()
                    .requestMatchers("/api/public/**").permitAll()
                    // Protected endpoints
                    .requestMatchers("/api/games/**", "/api/teams/**").authenticated()
                    .requestMatchers("/dashboard/**", "/profile/**").authenticated()
                    // All other requests require authentication
                    .anyRequest().authenticated()
            }
            .oauth2Login { oauth2 ->
                oauth2
                    .loginPage("/login")
                    .successHandler(authenticationSuccessHandler())
                    .failureUrl("/login?error=true")
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
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler()
        handler.setDefaultTargetUrl("/dashboard")
        handler.setAlwaysUseDefaultTargetUrl(false)
        return handler
    }
}