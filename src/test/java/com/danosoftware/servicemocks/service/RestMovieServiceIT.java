package com.danosoftware.servicemocks.service;

import com.danosoftware.servicemocks.dto.Movie;
import com.danosoftware.servicemocks.repository.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.danosoftware.servicemocks.helpers.MovieHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Confirms the behaviour of the FilterMovieService
 *
 * Since we only want to test our movie service implementation we need to mock the repository layer.
 *
 * @MockBean allows us to mock the repository and return our wanted list of test movies.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestMovieServiceIT {

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository repository;

    @Before
    public void setUp() throws Exception {
        when(repository.recommend()).thenReturn(allMovies());
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
