package com.danosoftware.movies;

import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.repository.MovieRepository;
import com.danosoftware.movies.repository.StubMovieRepository;
import com.danosoftware.movies.service.FilterMovieService;
import com.danosoftware.movies.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class MovieRecommendationApplication {

	public static void main(String[] args) {

		MovieRepository repository = new StubMovieRepository();
		MovieService service = new FilterMovieService(repository);


        // log results
        Logger LOGGER = LoggerFactory.getLogger(MovieRecommendationApplication.class);

		List<Movie> recommendations1 = service.recommend(
				Optional.of("Sci-Fi"),
				Optional.of(1972));

		LOGGER.info("Movies found: {}", recommendations1.size());
		recommendations1.stream().map(Movie::toString).forEach(LOGGER::info);

		List<Movie> recommendations2 = service.recommend(
				Optional.empty(),
				Optional.empty());

		LOGGER.info("Movies found: {}", recommendations2.size());
		recommendations2.stream().map(Movie::toString).forEach(LOGGER::info);
	}
}
