package com.solution.githubrepolist.exception;

import org.springframework.http.HttpStatus;

public class GithubNotFoundException extends RuntimeException {
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public GithubNotFoundException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
