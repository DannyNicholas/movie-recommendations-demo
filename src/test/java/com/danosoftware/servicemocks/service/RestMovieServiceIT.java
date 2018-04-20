package com.danosoftware.servicemocks.service;

import com.danosoftware.servicemocks.configuration.TestConfig;
import com.danosoftware.servicemocks.dto.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;
import java.util.Optional;

import static com.danosoftware.servicemocks.helpers.MovieHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Confirms the behaviour of the RestMovieService
 *
 * Since we only want to test our movie service implementation we need to mock the REST
 * responses returned by the back-end.
 *
 * MockRestServiceServer provides a mocked JSON response for any REST requests to the back-end
 * from our movie service.
 *
 * TestConfig class provides the configuration our RestMovieService needs.
 */
@RunWith(SpringRunner.class)
@RestClientTest({RestMovieService.class, TestConfig.class})
public class RestMovieServiceIT {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {

        String moviesJson =
                objectMapper.writeValueAsString(allMovies());

        server
                .expect(requestTo("http://test-host/api/movies-service/recommend"))
                .andRespond(withSuccess(moviesJson, MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnFilteredListForOptionalYear() {

        // find all recommended movies released in 1972
        List<Movie> movies = this.movieService.recommend(Optional.ofNullable(null), Optional.of(1972));

        assertThat(movies.size(), equalTo(2));
        assertThat(movies.get(0), equalTo(movieGodfather()));
        assertThat(movies.get(1), equalTo(movieSolaris()));
    }

    @Test
    public void shouldReturnFilteredListForOptionalGenre() {

        // find all recommended movies for the Sci-Fi genre
        List<Movie> movies = this.movieService.recommend(Optional.of("Sci-Fi"), Optional.ofNullable(null));

        assertThat(movies.size(), equalTo(2));
        assertThat(movies.get(0), equalTo(movieStarWars()));
        assertThat(movies.get(1), equalTo(movieSolaris()));
    }

    @Test
    public void shouldReturnFilteredListForOptionalYearAndGenre() {

        // find all recommended movies for the Sci-Fi genre released in 1972
        List<Movie> movies = this.movieService.recommend(Optional.of("Sci-Fi"), Optional.ofNullable(1972));

        assertThat(movies.size(), equalTo(1));
        assertThat(movies.get(0), equalTo(movieSolaris()));
    }

    @Test
    public void shouldReturnUnFilteredList() {

        // find all recommended movies
        List<Movie> movies = this.movieService.recommend(Optional.ofNullable(null), Optional.ofNullable(null));

        assertThat(movies.size(), equalTo(3));
        assertThat(movies.get(0), equalTo(movieStarWars()));
        assertThat(movies.get(1), equalTo(movieGodfather()));
        assertThat(movies.get(2), equalTo(movieSolaris()));
    }

}
