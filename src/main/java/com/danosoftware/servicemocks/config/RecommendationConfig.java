package com.danosoftware.servicemocks.config;

import com.danosoftware.servicemocks.service.StubMovieService;
import com.danosoftware.servicemocks.service.MovieService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RecommendationConfig {

    @Bean
    MovieService movieService(RestTemplate restTemplate) {
        //return new RestMovieService(restTemplate);
        return new StubMovieService();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
