package com.danosoftware.servicemocks.service;

import com.danosoftware.servicemocks.dto.Movie;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StubMovieService implements MovieService {
    @Override
    public List<Movie> recommend(Optional<String> genre, Optional<Integer> year) {
        return Arrays.asList(
                new Movie("Star Wars", "Fantasy", LocalDate.of(1977, 05, 4)),
                new Movie("Empire Strikes Back", "Fantasy", LocalDate.of(1980, 10, 21))
        );
    }

    @Override
    public Long addMovie(Movie movie) {
        return 23L;
    }

    @Override
    public Movie getMovie(Long id) {
        return new Movie("Star Wars", "Fantasy", LocalDate.of(1977, 05, 4));
    }
}
