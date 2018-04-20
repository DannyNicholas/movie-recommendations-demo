package com.danosoftware.servicemocks.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecommendationConfig {

    @Value("${movies.service.host}")
    private String movieServiceHost;

    @Bean
    @Qualifier("movieServiceHost")
    String movieServiceHost() {
        return movieServiceHost;
    }
}
