package com.example.rbac.impl.keycloak;

import java.util.Map;

import com.example.rbac.console.CompanyService.Integrations;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Configuration
public class KeycloakConfig {
    @Autowired
    private Environment env;

    @Bean
    public Keycloak keycloak(){
        String endpoint = env.getProperty("console.auth.endpoint");
        String realm = env.getProperty("console.auth.attributes.realm");
        String username = env.getProperty("console.auth.attributes.username");
        String password = env.getProperty("console.auth.attributes.password");
        String client_id = env.getProperty("console.auth.attributes.client-id");
        String client_secret = env.getProperty("console.auth.attributes.client-secret");

        KeycloakBuilder builder = KeycloakBuilder.builder();
        builder.serverUrl(endpoint).realm(realm).username(username)
                .password(password).clientId(client_id).clientSecret(client_secret);

        // KeycloakBuilder builder = KeycloakBuilder.builder();
        // builder.serverUrl("http://keycloak.local:8080/auth")
        //     .realm("master")
        //     .username("keycloak")
        //     .password("keycloak")
        //     .clientId("master-realm")
        //     // .clientSecret("141bfaa0-314d-4862-b466-cf4845fabb37");
        //     .clientSecret("49e1022f-5899-4836-bde4-3734776d69e1");
        Keycloak keycloak = builder.build();
        return keycloak;
    }

    @Bean
    public KeycloakService keycloakService(){
        return new KeycloakService();
    }

    @Component
    @ConfigurationProperties("")
    public static class IntegrationProperties {
        private Map<String, Integrations> integrations;

        public void setIntegrations(Map<String, Integrations> integrations) {
            this.integrations = integrations;
        }

        public Integrations getIntegrations(String name) {
            return integrations.get(name);
        }
    }
}