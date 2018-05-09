package com.danosoftware.movies.repository;

import com.danosoftware.movies.dto.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Provide stub responses to repository requests.
 * Only intended for local testing.
 */
public class StubMovieRepository implements MovieRepository {

    private static final Logger logger = LoggerFactory.getLogger(StubMovieRepository.class);

    // stub recommended movies
    private final List<Movie> stubMovies;

    public StubMovieRepository(final List<Movie> stubMovies) {
        this.stubMovies = stubMovies;
        logger.warn("Using Stub Movie Repository. Intended for local testing only.");
    }

    @Override
    public List<Movie> recommend() {
        return stubMovies;
    }
}
