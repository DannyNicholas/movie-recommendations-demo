package com.danosoftware.movies.repository;

import com.danosoftware.movies.config.RecommendationConfig;
import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.dto.MovieDocument;
import com.danosoftware.movies.repository.mongo.MongoDatabaseMovieRepository;
import com.danosoftware.movies.repository.mongo.MovieDataMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static com.danosoftware.movies.helpers.MovieHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Confirms the behaviour of the MongoDatabaseMovieRepository
 * <p>
 * Since we only want to test our movie repository implementation we need to mock the
 * responses returned by the database.
 * <p>
 * RecommendationConfig class provides the configuration our MongoDatabaseMovieRepository needs.
 */
@SpringBootTest(classes = {MongoDatabaseMovieRepository.class, RecommendationConfig.class})
@TestPropertySource(properties = "movies.service.database.initialise=false")
@ActiveProfiles("mongo")
public class MongoMovieRepositoryIT {

    @Autowired
    private MongoDatabaseMovieRepository repository;

    @MockitoBean
    private MovieDataMongoRepository dataRepository;


    @BeforeEach
    public void setUp() throws Exception {
        when(dataRepository.findByGenre(any(String.class))).thenReturn(allMovieDocuments());
        when(dataRepository.findById(any(String.class))).thenReturn(Optional.of(movieSolarisDocument()));

        MovieDocument created = movieSolarisDocument();
        created.setId("1");
        when(dataRepository.save(any(MovieDocument.class))).thenReturn(created);
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

        assertThat(createdMovieId, notNullValue());
    }

    @Test
    public void shouldGetMovie() {

        // get a new movie
        Optional<Movie> movie = repository.getMovie("1");

        assertThat(movie.get(), equalTo(movieSolaris()));
    }
}
