package com.danosoftware.movies.repository;

import com.danosoftware.movies.config.RecommendationConfig;
import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.dto.MovieEntity;
import com.danosoftware.movies.repository.jpa.H2DatabaseMovieRepository;
import com.danosoftware.movies.repository.jpa.MovieDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static com.danosoftware.movies.helpers.MovieHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Confirms the behaviour of the H2DatabaseMovieRepository
 * <p>
 * Since we only want to test our movie repository implementation we need to mock the
 * responses returned by the database.
 * <p>
 * RecommendationConfig class provides the configuration our H2DatabaseMovieRepository needs.
 */
@RestClientTest({H2DatabaseMovieRepository.class, RecommendationConfig.class})
@TestPropertySource(properties = "movies.service.database.initialise=false")
@ActiveProfiles("h2")
public class H2MovieRepositoryIT {

    @Autowired
    private MovieRepository repository;

    @MockitoBean
    private MovieDataRepository dataRepository;

    @BeforeEach
    public void setUp() throws Exception {
        when(dataRepository.findAll()).thenReturn(allMovieEntities());
        when(dataRepository.findById(any(Long.class))).thenReturn(Optional.of(movieSolarisEntity()));

        MovieEntity created = movieSolarisEntity();
        created.setId(1L);
        when(dataRepository.save(any(MovieEntity.class))).thenReturn(created);
    }

    @Test
    public void shouldReturnAllMovies() {

        // find all recommended movies
        List<Movie> movies = repository.recommend();

        assertThat(movies.size(), equalTo(3));
        assertThat(movies.get(0), equalTo(movieStarWars()));
        assertThat(movies.get(1), equalTo(movieGodfather()));
        assertThat(movies.get(2), equalTo(movieSolaris()));
    }

    @Test
    public void shouldSaveMovie() {

        // add a new movie
        String createdMovieId = repository.addMovie(movieSolaris());

        assertThat(createdMovieId, equalTo("1"));
    }

    @Test
    public void shouldGetMovie() {

        // get a new movie
        Optional<Movie> movie = repository.getMovie("1");

        assertThat(movie.get(), equalTo(movieSolaris()));
    }
}
