package com.danosoftware.movies.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
@Getter
public class Movie {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("genre")
    private final String genre;

    @JsonProperty("releaseDate")
    private final LocalDate releaseDate;

    @JsonCreator
    public Movie(
            @JsonProperty("name") String name,
            @JsonProperty("genre") String genre,
            @JsonProperty("releaseDate") LocalDate releaseDate) {

        this.name = name;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }
}
