package com.danosoftware.movies.repository;

import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.dto.MovieEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Database implementation using Postgres.
 */
@Repository
@Profile("postgres")
public class PostgresDatabaseMovieRepository implements MovieRepository {

    private final MovieDataRepository repository;

    public PostgresDatabaseMovieRepository(
            MovieDataRepository repository,
            List<Movie> defaultMovies,
            @Qualifier("initialiseDatabase") boolean initialiseDatabase) {

        this.repository = repository;

        if (initialiseDatabase) {
            addDefaultMovies(defaultMovies);
        }
    }

    // add default movies on start-up
    public void addDefaultMovies(List<Movie> defaultMovies) {
        defaultMovies.forEach(this::addMovie);
    }

    @Override
    public List<Movie> recommend() {
        List<Movie> movies = new ArrayList<>();
        repository.findAll().forEach((movieEntity) -> movies.add(
                new Movie(
                        movieEntity.getName(),
                        movieEntity.getGenre(),
                        movieEntity.getReleaseDate())
        ));
        return movies;
    }

    @Override
    public Long addMovie(Movie movie) {
        MovieEntity created = repository.save(new MovieEntity(movie));
        return created.getId();
    }

    @Override
    public Optional<Movie> getMovie(Long id) {
        Optional<MovieEntity> movieEntity = repository.findById(id);
        if (movieEntity.isPresent()) {
            MovieEntity movie = movieEntity.get();
            return Optional.of(new Movie(
                    movie.getName(),
                    movie.getGenre(),
                    movie.getReleaseDate()));
        }

        return Optional.empty();
    }
}
