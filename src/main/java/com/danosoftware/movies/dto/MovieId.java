package com.danosoftware.movies.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Builder
public class MovieId {

    @NotBlank
    @Schema(description = "Movie id", example = "100")
    @JsonProperty("id")
    private final Long id;
}
