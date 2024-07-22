package com.solution.githubrepolist.model.response;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(HttpStatus httpStatus) {
        this.status = httpStatus.value();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
