package com.danosoftware.movies.service;

import com.danosoftware.movies.dto.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    /**
     * Return a list of recommended movies.
     * Use optional genre and/or year to aid recommendation.
     */
    List<Movie> recommend(
            Optional<String> genre,
            Optional<Integer> year);

}
