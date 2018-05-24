package com.danosoftware.movies.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Movie Entity used for persistence.
 */
@Entity
@Table(name = "MOVIE")
@Getter
@Setter
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String genre;
    private LocalDate releaseDate;

    protected MovieEntity() {
    }

    public MovieEntity(Long id, String name, String genre, LocalDate releaseDate) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    public MovieEntity(Movie movie) {
        this.id = null;
        this.name = movie.getName();
        this.genre = movie.getGenre();
        this.releaseDate = movie.getReleaseDate();
    }
}
