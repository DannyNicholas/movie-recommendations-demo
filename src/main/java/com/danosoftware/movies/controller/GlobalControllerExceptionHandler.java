package com.danosoftware.movies.controller;

import com.danosoftware.movies.dto.ErrorResponse;
import com.danosoftware.movies.exception.MovieNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(MovieNotFoundException.class)
    public ErrorResponse handleNotFound(MovieNotFoundException e) {
        String error = String.format("Movie id %d not found.", e.getId());
        logger.error(error);
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .type("Not Found")
                .description(error)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleArgumentMismatch(MethodArgumentTypeMismatchException e) {
        String error = String.format("Received unsupported argument: '%s'", e.getValue());
        logger.error(error);
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .type("Bad Request")
                .description(error)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleArgumentNotValid(MethodArgumentNotValidException e) {
        String error = String.format("Argument not valid: '%s'", e.getMessage());
        logger.error(error);
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .type("Bad Request")
                .description(error)
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ErrorResponse unsupportedException(Exception e) {
        logger.error(e.getMessage(), e);
        return ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .type("Internal Server Error")
                .description("An unexpected error has occurred")
                .build();
    }
}
