package com.solution.githubrepolist.advice;

import com.solution.githubrepolist.exception.ExternalApiException;
import com.solution.githubrepolist.exception.GithubForbiddenException;
import com.solution.githubrepolist.model.response.ErrorResponse;
import com.solution.githubrepolist.exception.GithubNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(GithubNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGithubNotFound(GithubNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getHttpStatus(), ex.getMessage());
        LOGGER.error("GithubNotFoundException occurred: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(GithubForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(GithubForbiddenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getHttpStatus(), ex.getMessage());
        LOGGER.error("GithubForbiddenException occurred: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        LOGGER.error("IllegalArgumentException occurred: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(ExternalApiException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getHttpStatus(), ex.getMessage());
        LOGGER.error("Unknown external API exception occurred: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
}
