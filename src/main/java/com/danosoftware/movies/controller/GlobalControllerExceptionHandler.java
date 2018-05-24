package com.danosoftware.movies.controller;

import com.danosoftware.movies.exception.MovieNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(MovieNotFoundException.class)
    public String handleConflict(MovieNotFoundException e) {
        String error = "Movie id " + e.getId() + " not found.";
        logger.error(error);
        return error;
    }
}
