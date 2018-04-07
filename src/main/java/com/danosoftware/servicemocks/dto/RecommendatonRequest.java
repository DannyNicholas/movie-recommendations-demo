package com.danosoftware.servicemocks.dto;

import java.util.Optional;

/**
 * Represents a request for movie recommend.
 */
public class RecommendatonRequest {

    private final Optional<String> genre;
    private final Optional<Integer> year;

    public RecommendatonRequest(Optional<String> genre, Optional<Integer> year) {
        this.genre = genre;
        this.year = year;
    }
}
