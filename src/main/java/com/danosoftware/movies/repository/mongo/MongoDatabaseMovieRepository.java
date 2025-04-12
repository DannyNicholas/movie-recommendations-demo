package com.danosoftware.movies.repository.mongo;

import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.dto.MovieDocument;
import com.danosoftware.movies.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Database implementation using Postgres.
 */
@Repository
@Profile("mongo")
@Slf4j
public class MongoDatabaseMovieRepository implements MovieRepository {

    private final MovieDataMongoRepository repository;

    public MongoDatabaseMovieRepository(
            MovieDataMongoRepository repository,
            List<Movie> defaultMovies,
            @Qualifier("initialiseDatabase") boolean initialiseDatabase) {

        this.repository = repository;

        if (initialiseDatabase) {
            addDefaultMovies(defaultMovies);
        }
    }

    // add default movies on start-up
    public void addDefaultMovies(List<Movie> defaultMovies) {
        repository.deleteAll();
        defaultMovies.forEach(this::addMovie);
    }

    @Override
    public List<Movie> recommend() {
        log.info("Making Mongo request to retrieve movie recommendations.");
        return repository.findByGenre("Sci-Fi")
                .stream()
                .map(movie -> new Movie(
                        movie.getName(),
                        movie.getGenre(),
                        movie.getReleaseDate()))
                .toList();
    }

    @Override
    public String addMovie(Movie movie) {
        log.info("Making Mongo request to add new movie: {}", movie);
        MovieDocument created = repository.save(new MovieDocument(movie));
        return created.getId();
    }

    @Override
    public Optional<Movie> getMovie(String id) {
        log.info("Making Mongo request to get movie by id: {}", id);
        return repository.findById(id)
                .map(movie -> new Movie(
                        movie.getName(),
                        movie.getGenre(),
                        movie.getReleaseDate()));
    }
}
