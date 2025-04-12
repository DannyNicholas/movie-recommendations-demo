package com.danosoftware.movies.exception;

/**
 * Exception thrown if movie can not be found.
 */
public class MovieNotFoundException extends RuntimeException {

    private final String id;

    public MovieNotFoundException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
