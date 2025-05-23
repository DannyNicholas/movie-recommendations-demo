package com.danosoftware.movies.repository;

import com.danosoftware.movies.config.RecommendationConfig;
import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.repository.rest.RestMovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static com.danosoftware.movies.helpers.MovieHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Confirms the behaviour of the RestMovieRepository
 * <p>
 * Since we only want to test our movie repository implementation we need to mock the REST
 * responses returned by the back-end.
 * <p>
 * MockRestServiceServer provides a mocked JSON response for any REST requests to the back-end
 * from our movie repository.
 * <p>
 * RecommendationConfig class provides the configuration our RestMovieRepository needs.
 */
@RestClientTest({RestMovieRepository.class, RecommendationConfig.class})
@TestPropertySource(properties = "movies.service.host=http://test-host")
@ActiveProfiles("rest")
public class RestMovieRepositoryIT {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {

        String moviesJson =
                objectMapper.writeValueAsString(allMovies());

        server
                .expect(requestTo("http://test-host/api/movies-service/recommend"))
                .andRespond(withSuccess(moviesJson, MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnAllMovies() {

        // find all recommended movies
        List<Movie> movies = repository.recommend();

        assertThat(movies.size(), equalTo(3));
        assertThat(movies.get(0), equalTo(movieStarWars()));
        assertThat(movies.get(1), equalTo(movieGodfather()));
        assertThat(movies.get(2), equalTo(movieSolaris()));

        server.verify();
    }
}
