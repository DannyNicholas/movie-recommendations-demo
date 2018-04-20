package com.danosoftware.servicemocks.service;

import com.danosoftware.servicemocks.dto.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Movie Service implementation that retrieves movie
 * information from an external REST service.
 */
@Service
public class RestMovieService implements MovieService {

    private final RestTemplate restTemplate;
    private final String host;

    public RestMovieService(
            @Autowired RestTemplateBuilder restTemplateBuilder,
            @Autowired @Qualifier("movieServiceHost") String host) {
        this.restTemplate = restTemplateBuilder.build();
        this.host = host;
    }

    /**
     * Retrieve movies from back-end service and then filter by the optional criteria
     *
     * @param genre - optional genre filter
     * @param year - optional year released filter
     * @return list of filtered movies
     */
    @Override
    public List<Movie> recommend(Optional<String> genre, Optional<Integer> year) {

        ParameterizedTypeReference<List<Movie>> responseType = new ParameterizedTypeReference<List<Movie>>() {};

        URI uri = UriComponentsBuilder
                .fromUriString(host)
                .path("/api/movies-service/recommend")
                .build()
                .toUri();

        ResponseEntity<List<Movie>> response = restTemplate
                .exchange(uri, HttpMethod.GET, null, responseType);

        return response
                .getBody()
                .stream()
                .filter(matches(genre, year))
                .collect(Collectors.toList());
    }

    /**
     * Add a new movie
     *
     * @param movie
     * @return id of created movie
     */
    @Override
    public Long addMovie(Movie movie) {

        URI uri = UriComponentsBuilder
                .fromUriString(host)
                .path("/api/movies-service/add")
                .build()
                .toUri();

        HttpEntity<Movie> entity = new HttpEntity<>(movie);

        ResponseEntity<Long> response = restTemplate
                .exchange(uri, HttpMethod.POST, entity, Long.class);

        return response.getBody();
    }

    /**
     * Retrieve a specific movie using it's ID
     * @param id
     * @return movie
     */
    @Override
    public Movie getMovie(Long id) {

        URI uri = UriComponentsBuilder
                .fromUriString(host)
                .path("/api/movies-service/search/{id}")
                .buildAndExpand(id)
                .toUri();

        ResponseEntity<Movie> response = restTemplate
                .exchange(uri, HttpMethod.GET, null, Movie.class);

        return response.getBody();
    }

    /**
     * Create a predicate that will allow filtering on optional genre and year released.
     * If no genre or year is provided all movies will pass the filter.
     */
    public static Predicate<Movie> matches(Optional<String> genre, Optional<Integer> year) {

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
