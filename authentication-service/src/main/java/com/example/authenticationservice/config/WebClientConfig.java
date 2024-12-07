package com.example.authenticationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.config.keycloak.tokenUrl}")
    private String keycloakTokenUrl;

    @Bean("keycloakWebClient")
    public WebClient keycloakWebClient() {
        return WebClient.
                builder().
                baseUrl("http://keycloak:9982/realms/auth_service_realm/protocol/openid-connect/token").
                build();
    }
}
