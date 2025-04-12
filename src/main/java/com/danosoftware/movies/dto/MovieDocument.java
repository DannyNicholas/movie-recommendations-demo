package com.danosoftware.movies.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Representation of a movie within a Mongo DB
 */
@Document(collection = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String name;
    private String genre;
    private LocalDate releaseDate;

    public MovieDocument(Movie movie) {
        this.id = null;
        this.name = movie.getName();
        this.genre = movie.getGenre();
        this.releaseDate = movie.getReleaseDate();
    }
}
