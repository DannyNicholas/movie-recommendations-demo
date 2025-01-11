package com.danosoftware.movies.controller;

import com.danosoftware.movies.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.danosoftware.movies.helpers.MovieHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Confirms the response received from the controller when hit by an API request.
 * <p>
 * This test uses MockMvc to test the controller without starting a full HTTP server.
 * MockMvc will provide a HTTP response so we will use jsonPath to check the contents
 * of the message body.
 * <p>
 * Since we are only testing the controller, we want to mock the service layer.
 *
 * @MockBean replaces the normal MovieService instance created by Spring with a mock.
 * This gets injected into the controller instead.
 */
@WebMvcTest(RecommendationController.class)
public class RecommendationControllerMvcIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @Test
    public void shouldReturnAllMoviesJson() throws Exception {

        given(movieService.recommend(any(Optional.class), any(Optional.class))).willReturn(allMovies());

        mockMvc.perform(get("/api/movies/recommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", equalTo(movieStarWars().getName())))
                .andExpect(jsonPath("$[0].genre", equalTo(movieStarWars().getGenre())))
                .andExpect(jsonPath("$[0].releaseDate", equalTo(movieStarWars().getReleaseDate().toString())))
                .andExpect(jsonPath("$[1].name", equalTo(movieGodfather().getName())))
                .andExpect(jsonPath("$[1].genre", equalTo(movieGodfather().getGenre())))
                .andExpect(jsonPath("$[1].releaseDate", equalTo(movieGodfather().getReleaseDate().toString())))
                .andExpect(jsonPath("$[2].name", equalTo(movieSolaris().getName())))
                .andExpect(jsonPath("$[2].genre", equalTo(movieSolaris().getGenre())))
                .andExpect(jsonPath("$[2].releaseDate", equalTo(movieSolaris().getReleaseDate().toString())));
    }
}
