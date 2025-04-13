package com.danosoftware.movies.repository.stub;

import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.exception.MovieNotFoundException;
import com.danosoftware.movies.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Provide stub responses to repository requests.
 * Only intended for local testing.
 */
@Repository
@Profile("stub")
@Slf4j
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
        log.info("Making Stub request to retrieve movie recommendations.");
        return movies;
    }

    @Override
    public String addMovie(Movie movie) {
        log.info("Making Stub request to add new movie: {}", movie);
        return UUID.randomUUID().toString();
    }

    @Override
    public Optional<Movie> getMovie(String id) {
        log.info("Making Stub request to get movie by id: {}", id);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException(id);
        }
        return Optional.of(movies.get(0));
    }
}
