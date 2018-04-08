package com.danosoftware.servicemocks.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RecommendationConfig {

    @Value("${movies.service.host}")
    private String movieServiceHost;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Qualifier("movieServiceHost")
    String movieServiceHost() {
        return movieServiceHost;
    }
}
