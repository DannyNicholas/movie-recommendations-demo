package com.danosoftware.movies.controller;

import com.danosoftware.movies.dto.ErrorResponse;
import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.dto.MovieId;
import com.danosoftware.movies.exception.MovieNotFoundException;
import com.danosoftware.movies.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movies", description = "Movies API endpoints")
public class RecommendationController {

    private final MovieService service;

    public RecommendationController(@Autowired MovieService service) {
        this.service = service;
    }

    /**
     * Get movie recommendations.
     */
    @Operation(summary = "Get movie recommendations")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found movie recommendations",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Movie.class)))
                    })
    })
    @GetMapping("/recommendations")
    public @ResponseBody List<Movie> recommend(
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "year", required = false) Integer year) {

        return service.recommend(
                Optional.ofNullable(genre),
                Optional.ofNullable(year));
    }

    /**
     * Get a movie using provided id.
     */
    @Operation(summary = "Get a movie by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the movie",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Movie.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Movie not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @GetMapping("/{movieId}")
    public @ResponseBody Movie getMovie(
            @PathVariable String movieId) {

        return service.getMovie(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }

    /**
     * Add a new movie and return created id.
     */
    @Operation(summary = "Add a new movie")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created the movie",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MovieId.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody MovieId add(
            @RequestBody @Valid Movie movie) {

        return MovieId.builder()
                .id(service.addMovie(movie))
                .build();
    }
}
