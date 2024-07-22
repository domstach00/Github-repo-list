package com.solution.githubrepolist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GithubValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(GithubValidator.class);

    private final int usernameMaxLength;

    private final int usernameMaxLengthForLogging;

    public GithubValidator(@Value("${github.max-username-length:39}") int usernameMaxLength,
                           @Value("${github.max-username-length-for-logging:50}") int usernameMaxLengthForLogging) {
        this.usernameMaxLength = usernameMaxLength;
        this.usernameMaxLengthForLogging = usernameMaxLengthForLogging;
    }

    public void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            LOGGER.error("Username cannot be null or blank");
            throw new IllegalArgumentException("Username cannot be null or blank");
        }

        if (username.length() > getUsernameMaxLength()) {
            String trimmedUsername  = truncateUsernameForLogging(username);
            LOGGER.error("Username `{}` is invalid, it cannot be longer then {} characters",
                    trimmedUsername, getUsernameMaxLength());
            throw new IllegalArgumentException("Username cannot be longer than %s characters"
                    .formatted(getUsernameMaxLength()));
        }
    }

    public String truncateUsernameForLogging(String username) {
        return username.length() > getUsernameMaxLengthForLogging()
                ? username.substring(0, getUsernameMaxLengthForLogging()) + "..."
                : username;
    }

    protected int getUsernameMaxLength() {
        return this.usernameMaxLength;
    }

    protected int getUsernameMaxLengthForLogging() {
        return this.usernameMaxLengthForLogging;
    }
}
