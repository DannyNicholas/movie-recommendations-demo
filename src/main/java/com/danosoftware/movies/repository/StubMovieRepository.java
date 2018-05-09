package com.danosoftware.movies.repository;

import com.danosoftware.movies.dto.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Provide stub responses to repository requests.
 * Only intended for local testing.
 */
public class StubMovieRepository implements MovieRepository {

    private static final Logger logger = LoggerFactory.getLogger(StubMovieRepository.class);

    public StubMovieRepository() {
        logger.warn("Using Stub Movie Repository. Intended for local testing only.");
    }

    @Override
    public List<Movie> recommend() {
        return Arrays.asList(
                new Movie("Star Wars","Sci-Fi", LocalDate.of(1977, 5, 25)),
                new Movie("The Godfather","Crime",LocalDate.of(1972, 3, 24)),
                new Movie("Solaris","Sci-Fi",LocalDate.of(1972, 9, 26))
        );
    }
}
