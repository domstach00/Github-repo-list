package com.solution.githubrepolist.exception;

import org.springframework.http.HttpStatus;

public class GithubForbiddenException extends RuntimeException {
    private static final HttpStatus httpStatus = HttpStatus.FORBIDDEN;

    public GithubForbiddenException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
