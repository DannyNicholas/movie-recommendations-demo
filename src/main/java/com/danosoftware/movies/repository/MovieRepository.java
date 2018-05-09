package com.danosoftware.movies.repository;

import com.danosoftware.movies.dto.Movie;

import java.util.List;

public interface MovieRepository {

    /**
     * Return a list of recommended movies.
     */
    List<Movie> recommend();
}
