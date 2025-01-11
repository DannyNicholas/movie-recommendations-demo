package com.danosoftware.movies.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
@Getter
public class Movie {

    @NotBlank
    @Schema(description = "Name of movie", example = "Star Wars")
    @JsonProperty("name")
    private final String name;

    @NotBlank
    @Schema(description = "Genre of movie", example = "Sci-Fi")
    @JsonProperty("genre")
    private final String genre;

    @NotNull
    @Schema(description = "Release date of movie", example = "1977-05-25")
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
