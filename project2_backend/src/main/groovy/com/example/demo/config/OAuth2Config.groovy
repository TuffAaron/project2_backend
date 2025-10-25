package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter
import org.springframework.util.StringUtils
import org.springframework.web.client.RestTemplate

import java.time.Instant
import java.time.temporal.ChronoUnit

@Configuration
class OAuth2Config {

    @Bean
    OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient client = new DefaultAuthorizationCodeTokenResponseClient()
        
        // Create custom token response converter to handle GitHub's string scope format
        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseConverter = 
            new OAuth2AccessTokenResponseHttpMessageConverter()
        
        // Add custom converter for parameters
        tokenResponseConverter.setAccessTokenResponseConverter(new GitHubAccessTokenResponseConverter())
        
        RestTemplate restTemplate = new RestTemplate([
            new FormHttpMessageConverter(),
            tokenResponseConverter
        ])
        
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler())
        client.setRestOperations(restTemplate)
        
        return client
    }
    
    /**
     * Custom converter to handle GitHub's OAuth2 token response format.
     * GitHub returns scope as a string instead of an array, which causes parsing issues.
     */
    static class GitHubAccessTokenResponseConverter implements Converter<Map<String, Object>, OAuth2AccessTokenResponse> {
        
        @Override
        OAuth2AccessTokenResponse convert(Map<String, Object> tokenResponseParameters) {
            String accessToken = tokenResponseParameters.get(OAuth2ParameterNames.ACCESS_TOKEN)
            
            OAuth2AccessToken.TokenType accessTokenType = OAuth2AccessToken.TokenType.BEARER
            
            long expiresIn = 0
            if (tokenResponseParameters.containsKey(OAuth2ParameterNames.EXPIRES_IN)) {
                try {
                    expiresIn = Long.parseLong(tokenResponseParameters.get(OAuth2ParameterNames.EXPIRES_IN).toString())
                } catch (NumberFormatException ex) {
                    // Default to 0 if parsing fails
                }
            }
            
            Set<String> scopes = Collections.emptySet()
            if (tokenResponseParameters.containsKey(OAuth2ParameterNames.SCOPE)) {
                Object scopeObj = tokenResponseParameters.get(OAuth2ParameterNames.SCOPE)
                if (scopeObj instanceof String) {
                    String scope = (String) scopeObj
                    scopes = new LinkedHashSet<>(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")))
                } else if (scopeObj instanceof Collection) {
                    scopes = new LinkedHashSet<>((Collection<String>) scopeObj)
                }
            }
            
            String refreshToken = tokenResponseParameters.get(OAuth2ParameterNames.REFRESH_TOKEN)
            
            Map<String, Object> additionalParameters = new LinkedHashMap<>()
            tokenResponseParameters.entrySet().each { entry ->
                if (![OAuth2ParameterNames.ACCESS_TOKEN, OAuth2ParameterNames.TOKEN_TYPE,
                      OAuth2ParameterNames.EXPIRES_IN, OAuth2ParameterNames.REFRESH_TOKEN,
                      OAuth2ParameterNames.SCOPE].contains(entry.key)) {
                    additionalParameters.put(entry.key, entry.value)
                }
            }
            
            Instant issuedAt = Instant.now()
            Instant expiresAt = (expiresIn > 0) ? issuedAt.plus(expiresIn, ChronoUnit.SECONDS) : null
            
            return OAuth2AccessTokenResponse.withToken(accessToken)
                .tokenType(accessTokenType)
                .expiresIn(expiresIn)
                .scopes(scopes)
                .refreshToken(refreshToken)
                .additionalParameters(additionalParameters)
                .build()
        }
    }
}
