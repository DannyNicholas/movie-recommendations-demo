package com.danosoftware.servicemocks.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

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
