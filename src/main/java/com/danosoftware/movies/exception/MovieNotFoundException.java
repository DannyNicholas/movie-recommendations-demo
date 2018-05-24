package com.danosoftware.movies.exception;

/**
 * Exception thrown if movie can not be found.
 */
public class MovieNotFoundException extends RuntimeException {

    private final Long id;

    public MovieNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
