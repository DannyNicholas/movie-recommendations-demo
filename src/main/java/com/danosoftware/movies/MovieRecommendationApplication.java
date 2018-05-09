package com.danosoftware.movies;

import com.danosoftware.movies.dto.Movie;
import com.danosoftware.movies.repository.MovieRepository;
import com.danosoftware.movies.repository.StubMovieRepository;
import com.danosoftware.movies.service.FilterMovieService;
import com.danosoftware.movies.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MovieRecommendationApplication {

	public static void main(String[] args) {

		List<Movie> stubMovies = stubMovies();
		MovieRepository repository = new StubMovieRepository(stubMovies);
		MovieService service = new FilterMovieService(repository);

		test(service);
	}

	private static void test(MovieService service) {

		// log results
		Logger LOGGER = LoggerFactory.getLogger(MovieRecommendationApplication.class);

		List<Movie> recommendations1 = service.recommend(
				Optional.of("Sci-Fi"),
				Optional.of(1972));

		LOGGER.info("Movies found: {}", recommendations1.size());
		recommendations1.stream().map(Movie::toString).forEach(LOGGER::info);
	}

	private static List<Movie> stubMovies() {
		return Arrays.asList(
				new Movie("Star Wars","Sci-Fi", LocalDate.of(1977, 5, 25)),
				new Movie("The Godfather","Crime",LocalDate.of(1972, 3, 24)),
				new Movie("Solaris","Sci-Fi",LocalDate.of(1972, 9, 26))
		);
	}
}
