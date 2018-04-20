package com.danosoftware.servicemocks.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    @Qualifier("movieServiceHost")
    String movieServiceHost() {
        return "http://test-host";
    }
}
