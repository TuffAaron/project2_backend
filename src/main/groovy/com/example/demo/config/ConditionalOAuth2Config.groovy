package com.example.demo.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = "spring.security.oauth2.client.registration.github.client-id")
class ConditionalOAuth2Config {
    // This configuration only loads when OAuth2 credentials are configured
    // Prevents OAuth2 autoconfiguration from failing when credentials are missing
}
