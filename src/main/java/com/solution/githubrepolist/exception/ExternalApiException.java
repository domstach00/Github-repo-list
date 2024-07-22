package com.solution.githubrepolist.exception;

import org.springframework.http.HttpStatus;

public class ExternalApiException extends RuntimeException {
    private static final HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public ExternalApiException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
