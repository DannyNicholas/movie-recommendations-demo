package com.danosoftware.movies.controller;

import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/movies")
public class RecommendationController {

    private final MovieService service;

    public RecommendationController(MovieService service) {
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
}
