package com.danosoftware.movies.helpers;

import com.danosoftware.movies.dto.Movie;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Helper class that provides example movies for testing.
 */
public class MovieHelper {

    public static final Movie movieStarWars() {
        return new Movie(
                "Star Wars",
                "Sci-Fi",
                LocalDate.of(1977, 5, 25)
        );
    }

    public static final Movie movieGodfather() {
        return new Movie(
                "The Godfather",
                "Crime",
                LocalDate.of(1972, 3, 24)
        );
    }

    public static final Movie movieSolaris() {
        return new Movie(
                "Solaris",
                "Sci-Fi",
                LocalDate.of(1972, 9, 26)
        );
    }

    public static final List<Movie> allMovies() {
        return Arrays.asList(movieStarWars(), movieGodfather(), movieSolaris());
    }
}
