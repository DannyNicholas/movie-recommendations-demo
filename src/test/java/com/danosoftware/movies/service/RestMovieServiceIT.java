package com.danosoftware.movies.service;

import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.masking.UserService;
import com.danosoftware.movies.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static com.danosoftware.movies.helpers.MovieHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Confirms the behaviour of the FilterMovieService
 * <p>
 * Since we only want to test our movie service implementation we need to mock the repository layer.
 *
 * @MockBean allows us to mock the repository and return our wanted list of test movies.
 */
@SpringBootTest
@Slf4j
public class RestMovieServiceIT {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @MockitoBean
    private MovieRepository repository;

    @BeforeEach
    public void setUp() throws Exception {
        when(repository.recommend()).thenReturn(allMovies());
    }

    @Test
    public void shouldReturnFilteredListForOptionalYear() {

        // find all recommended movies released in 1972
        List<Movie> movies = this.movieService.recommend(Optional.empty(), Optional.of(1972));

        assertThat(movies.size(), equalTo(2));
        assertThat(movies.get(0), equalTo(movieGodfather()));
        assertThat(movies.get(1), equalTo(movieSolaris()));
    }

    @Test
    public void shouldReturnFilteredListForOptionalGenre() {

        // find all recommended movies for the Sci-Fi genre
        List<Movie> movies = this.movieService.recommend(Optional.of("Sci-Fi"), Optional.empty());

        assertThat(movies.size(), equalTo(2));
        assertThat(movies.get(0), equalTo(movieStarWars()));
        assertThat(movies.get(1), equalTo(movieSolaris()));
    }

    @Test
    public void shouldReturnFilteredListForOptionalYearAndGenre() {

        // find all recommended movies for the Sci-Fi genre released in 1972
        List<Movie> movies = this.movieService.recommend(Optional.of("Sci-Fi"), Optional.of(1972));

        assertThat(movies.size(), equalTo(1));
        assertThat(movies.get(0), equalTo(movieSolaris()));
    }

    @Test
    public void shouldReturnUnFilteredList() {

        // find all recommended movies
        List<Movie> movies = this.movieService.recommend(Optional.empty(), Optional.empty());

        assertThat(movies.size(), equalTo(3));
        assertThat(movies.get(0), equalTo(movieStarWars()));
        assertThat(movies.get(1), equalTo(movieGodfather()));
        assertThat(movies.get(2), equalTo(movieSolaris()));
    }

    @Test
    public void test() {
        userService.log();
    }
}
