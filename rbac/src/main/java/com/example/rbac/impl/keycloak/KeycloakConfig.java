package com.example.rbac.impl.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Bean
    public Keycloak keycloak(){
        KeycloakBuilder builder = KeycloakBuilder.builder();
        builder.serverUrl("http://keycloak.local:8080/auth")
            .realm("master")
            .username("keycloak")
            .password("keycloak")
            .clientId("master-realm")
            .clientSecret("141bfaa0-314d-4862-b466-cf4845fabb37");
        Keycloak keycloak = builder.build();
        return keycloak;
    }

    @Bean
    public KeycloakService keycloakService(){
        return new KeycloakService();
    }
}