package com.danosoftware.servicemocks.controller;

import com.danosoftware.servicemocks.dto.Health;
import com.danosoftware.servicemocks.dto.Movie;
import com.danosoftware.servicemocks.service.MovieService;
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
    public List<Movie> recommend(
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
    public Movie getMovie(
            @PathVariable Long movieId) {

        return service.getMovie(movieId);
    }

    /**
     * Add a new movie and return created id.
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus( HttpStatus.CREATED )
    public Long add(
            @RequestBody Movie movie) {

        return service.addMovie(movie);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/health")
    public Health health() {

        return new Health("I am alive");
    }
}
