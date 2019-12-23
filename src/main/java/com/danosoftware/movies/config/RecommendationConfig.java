package com.danosoftware.movies.config;

import com.danosoftware.movies.dto.Movie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configuration
public class RecommendationConfig {

    @Bean
    @Profile("rest")
    @Qualifier("movieServiceHost")
    String movieServiceHost(
            @Value("${movies.service.host}") String movieServiceHost) {

        return movieServiceHost;
    }

    @Bean
    @Profile("stub")
    List<Movie> stubMovies () {
        return Arrays.asList(
                new Movie("Star Wars", "Sci-Fi", LocalDate.of(1977, 5, 25)),
                new Movie("The Godfather", "Crime", LocalDate.of(1972, 3, 24)),
                new Movie("Solaris", "Sci-Fi", LocalDate.of(1972, 9, 26))
        );
    }

    @Bean
    @Profile("h2")
    List<Movie> defaultMovies () {
        return Arrays.asList(
                new Movie("Star Wars", "Sci-Fi", LocalDate.of(1977, 5, 25)),
                new Movie("The Godfather", "Crime", LocalDate.of(1972, 3, 24)),
                new Movie("Solaris", "Sci-Fi", LocalDate.of(1972, 9, 26)),
                new Movie("Blade Runner", "Sci-Fi", LocalDate.of(1982, 6, 25))
        );
    }

    @Bean
    @Profile("postgres")
    List<Movie> defaultPostgresMovies () {
        return Arrays.asList(
                new Movie("Star Wars", "Sci-Fi", LocalDate.of(1977, 5, 25)),
                new Movie("The Godfather", "Crime", LocalDate.of(1972, 3, 24)),
                new Movie("Solaris", "Sci-Fi", LocalDate.of(1972, 9, 26)),
                new Movie("Blade Runner", "Sci-Fi", LocalDate.of(1982, 6, 25))
        );
    }

    @Bean
    @Profile("h2")
    @Qualifier("initialiseDatabase")
    boolean initialiseDatabase(@Value("${movies.service.database.initialise}") boolean initialiseDatabase) {
        return initialiseDatabase;
    }

    @Bean
    @Profile("postgres")
    @Qualifier("initialiseDatabase")
    boolean initialiseDatabasePostgres(@Value("${movies.service.database.initialise}") boolean initialiseDatabase) {
        return initialiseDatabase;
    }
}
