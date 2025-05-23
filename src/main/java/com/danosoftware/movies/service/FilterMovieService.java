package com.danosoftware.movies.service;

import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Movie Service implementation that retrieves movie
 * information from repository and filters on wanted criteria.
 */
@Service
public class FilterMovieService implements MovieService {

    private final MovieRepository repository;

    @Autowired
    public FilterMovieService(final MovieRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieve movies from repository and then filter by the optional criteria
     *
     * @param genre - optional genre filter
     * @param year  - optional year released filter
     * @return list of filtered movies
     */
    @Override
    public List<Movie> recommend(Optional<String> genre, Optional<Integer> year) {

        return repository.recommend()
                .stream()
                .filter(matches(genre, year))
                .collect(Collectors.toList());
    }

    /**
     * Add a new movie and return ID
     */
    @Override
    public String addMovie(Movie movie) {

        return repository.addMovie(movie);
    }

    /**
     * Retrieve a specific movie using ID
     */
    @Override
    public Optional<Movie> getMovie(String id) {

        return repository.getMovie(id);
    }

    /**
     * Create a predicate that will allow filtering on optional genre and year released.
     * If no genre or year is provided all movies will pass the filter.
     */
    private static Predicate<Movie> matches(Optional<String> genre, Optional<Integer> year) {

        Predicate<Movie> result = movie -> true;

        if (genre.isPresent()) {
            result = result.and(movie -> genre.get().equals(movie.getGenre()));
        }

        if (year.isPresent()) {
            result = result.and(movie -> year.get().equals(movie.getReleaseDate().getYear()));
        }

        return result;
    }
}
