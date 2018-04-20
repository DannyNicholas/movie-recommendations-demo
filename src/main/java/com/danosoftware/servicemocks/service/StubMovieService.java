package com.danosoftware.servicemocks.service;

import com.danosoftware.servicemocks.dto.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Stub movie service for local testing only.
 */
@Service
@Profile("local")
@Primary
public class StubMovieService implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(StubMovieService.class);

    public StubMovieService() {
        logger.warn("Using Stub Movie Service. Intended for local testing only.");
    }

    @Override
    public List<Movie> recommend(Optional<String> genre, Optional<Integer> year) {
        return Arrays.asList(
                new Movie("Star Wars", "Fantasy", LocalDate.of(1977, 05, 4)),
                new Movie("Empire Strikes Back", "Fantasy", LocalDate.of(1980, 10, 21))
        );
    }

    @Override
    public Long addMovie(Movie movie) {
        return 23L;
    }

    @Override
    public Movie getMovie(Long id) {
        return new Movie("Star Wars", "Fantasy", LocalDate.of(1977, 05, 4));
    }
}
