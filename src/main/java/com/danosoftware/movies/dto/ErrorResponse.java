package com.danosoftware.movies.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {

    @Schema(description = "Error code", example = "400")
    private final int code;

    @Schema(description = "Error type", example = "Bad Request")
    private final String type;

    @Schema(description = "Error description", example = "Supplied parameters were invalid")
    private final String description;
}
