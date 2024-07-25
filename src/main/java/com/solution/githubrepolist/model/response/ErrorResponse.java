package com.solution.githubrepolist.model.response;

import org.springframework.http.HttpStatus;

public record ErrorResponse(int status, String message) {
    public ErrorResponse(HttpStatus status, String message) {
        this(status.value(), message);
    }
}
