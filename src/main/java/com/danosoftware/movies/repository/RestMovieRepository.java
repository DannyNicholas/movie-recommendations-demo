package com.danosoftware.movies.repository;

import com.danosoftware.movies.dto.Movie;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Movie Repository implementation that retrieves movie
 * information from an external REST service.
 */
public class RestMovieRepository implements MovieRepository {

    private final RestTemplate restTemplate;
    private final String host = "http://api.movie-service.com";

    public RestMovieRepository(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
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
}
