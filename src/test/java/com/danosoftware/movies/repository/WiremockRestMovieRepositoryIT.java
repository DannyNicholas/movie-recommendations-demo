package com.danosoftware.movies.repository;

import com.danosoftware.movies.dto.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests repository behaviour with a Rest service.
 * Rest service responses provided using Wiremock.
 * <p>
 * Some tests use configured Wiremock mappings.
 * Wiremock mappings are in /resources/mappings/...
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"movies.service.host=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock
@ActiveProfiles("rest")
public class WiremockRestMovieRepositoryIT {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private ObjectMapper mapper;

    // Test using a manually created Wiremock stub response
    @Test
    public void retrieveGenericMovie() throws Exception {

        Movie movie = new Movie("movie-name", "movie-genre", LocalDate.now());
        String json = mapper.writeValueAsString(movie);

        // Stubbing for WireMock to return our movie REST response for movie id 1
        stubFor(
                get(
                        urlEqualTo("/api/movies-service/search/1")
                )
                        .willReturn(
                                aResponse()
                                        .withStatus(HttpStatus.OK.value())
                                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(json)
                        )
        );

        // assert repository provided the expected response for movie id 1
        assertThat(repository.getMovie("1"))
                .isEqualTo(Optional.of(movie));
    }

    @Test
    public void getStarWars() {

        // Request movie 1972 (Wiremock should provide REST response for Star Wars)
        Movie movie = repository.getMovie("1972").orElseThrow(RuntimeException::new);

        assertThat(movie.getName()).isEqualTo("Star Wars");
        assertThat(movie.getGenre()).isEqualTo("Sci-Fi");
        assertThat(movie.getReleaseDate()).isEqualTo(LocalDate.of(1977, 5, 25));
    }

    @Test
    public void getUnknownMovie() {

        // Wiremock should return 404 for an unknown movie id
        // Repository should throw an exception
        assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> repository.getMovie("9999"));
    }

    @Test
    public void postTheGodfather() {

        // Post The Godfather movie (Wiremock should assign movie id 23)
        Movie movie = new Movie("The Godfather", "Crime", LocalDate.of(1972, 3, 24));
        String id = repository.addMovie(movie);

        assertThat(id).isEqualTo("23");
    }

    @Test
    public void getRecommendations() {

        // Request recommendations (Wiremock should provide Star Wars and The Godfather)
        List<Movie> movies = repository.recommend();
        assertThat(movies.size()).isEqualTo(2);
        assertThat(movies).containsExactlyInAnyOrder(
                new Movie("Star Wars", "Sci-Fi", LocalDate.of(1977, 5, 25)),
                new Movie("The Godfather", "Crime", LocalDate.of(1972, 3, 24))
        );
    }
}
