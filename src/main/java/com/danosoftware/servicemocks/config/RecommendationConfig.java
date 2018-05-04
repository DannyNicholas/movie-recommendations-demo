package com.danosoftware.servicemocks.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RecommendationConfig {

    @Bean
    @Profile("rest")
    @Qualifier("movieServiceHost")
    String movieServiceHost(
            @Value("${movies.service.host}") String movieServiceHost) {

        return movieServiceHost;
    }
}
