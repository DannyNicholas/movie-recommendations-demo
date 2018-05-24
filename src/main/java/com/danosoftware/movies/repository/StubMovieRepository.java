package com.danosoftware.movies.repository;

import com.danosoftware.movies.dto.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Provide stub responses to repository requests.
 * Only intended for local testing.
 */
@Repository
@Profile("stub")
public class StubMovieRepository implements MovieRepository {

    private static final Logger logger = LoggerFactory.getLogger(StubMovieRepository.class);

    // stub recommended movies
    private final List<Movie> movies;

    public StubMovieRepository(final List<Movie> movies) {
        this.movies = movies;
        logger.warn("Using Stub Movie Repository. Intended for local testing only.");
    }

    @Override
    public List<Movie> recommend() {
        return movies;
    }

    @Override
    public Long addMovie(Movie movie) {
        return 23L;
    }

    @Override
    public Optional<Movie> getMovie(Long id) {
        return Optional.of(movies.get(0));
    }
}
