package com.danosoftware.movies.repository;

import com.danosoftware.movies.dto.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Movie Repository implementation that retrieves movie
 * information from an external REST service.
 */
@Repository
@Profile("rest")
public class RestMovieRepository implements MovieRepository {

    private final RestTemplate restTemplate;
    private final String host;

    @Autowired
    public RestMovieRepository(
            RestTemplateBuilder restTemplateBuilder,
            @Qualifier("movieServiceHost") String host) {
        this.restTemplate = restTemplateBuilder.build();
        this.host = host;
    }

    /**
     * Retrieve recommended movies from REST service
     *
     * @return list of recommended movies
     */
    @Override
    public List<Movie> recommend() {

        ParameterizedTypeReference<List<Movie>> responseType = new ParameterizedTypeReference<List<Movie>>() {};

        URI uri = UriComponentsBuilder
                .fromUriString(host)
                .path("/api/movies-service/recommend")
                .build()
                .toUri();

        ResponseEntity<List<Movie>> response = restTemplate
                .exchange(uri, HttpMethod.GET, null, responseType);

        return response.getBody();
    }

    /**
     * Send a new movie to the REST service
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
     * Retrieve a specific movie from the REST service using it's ID
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
}
