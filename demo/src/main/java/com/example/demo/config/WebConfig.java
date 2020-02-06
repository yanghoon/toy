package com.example.demo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                // .basicAuthorization("user", "name") // Optional Basic auth example
                // .interceptors(new MyCustomInterceptor()) // Optional Custom interceptors, etc..
                .build();
    }
}