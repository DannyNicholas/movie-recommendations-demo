package com.danosoftware.movies.controller;

import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.exception.MovieNotFoundException;
import com.danosoftware.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class RecommendationController {

    private final MovieService service;

    public RecommendationController(@Autowired MovieService service) {
        this.service = service;
    }

    /**
     * Get movie recommendations.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/recommendations")
    public @ResponseBody List<Movie> recommend(
            @RequestParam(value="genre", required = false) String genre,
            @RequestParam(value="year", required = false) Integer year) {

        return service.recommend(
                Optional.ofNullable(genre),
                Optional.ofNullable(year));
    }

    /**
     * Get a movie using provided id.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{movieId}")
    public @ResponseBody Movie getMovie(
            @PathVariable Long movieId) {

        Optional<Movie> movie = service.getMovie(movieId);
        if (movie.isPresent()) {
            return movie.get();
        }

        throw new MovieNotFoundException(movieId);
    }

    /**
     * Add a new movie and return created id.
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus( HttpStatus.CREATED )
    public @ResponseBody Long add(
            @RequestBody Movie movie) {

        return service.addMovie(movie);
    }
}
