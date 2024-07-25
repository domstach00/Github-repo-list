package com.solution.githubrepolist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GithubValidatorTest {
    private final int usernameMaxLength = 39;

    private final int usernameMaxLengthForLogging = 50;

    private GithubValidator githubValidator;

    @BeforeEach
    void setUp() {
        githubValidator = new GithubValidator(usernameMaxLength, usernameMaxLengthForLogging);
    }

    @Test
    void validateUsername_WhenNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            githubValidator.validateUsername(null);
        }, "Username cannot be null or blank");
    }

    @Test
    void validateUsername_WhenBlankUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            githubValidator.validateUsername("");
        }, "Username cannot be null or blank");
    }

    @Test
    void validateUsername_WhenTooLongUsername() {
        String longUsername = "a".repeat(usernameMaxLength + usernameMaxLengthForLogging);
        assertThrows(IllegalArgumentException.class, () -> githubValidator.validateUsername(longUsername));
    }

    @Test
    void validateUsername_WhenValidUsername() {
        String validUsername = "validUsername";
        assertDoesNotThrow(() -> githubValidator.validateUsername(validUsername));
    }

    @Test
    void truncateUsernameForLogging_WhenUsernameShorterThanMaxLoggingLength() {
        String username = "a".repeat(usernameMaxLengthForLogging);
        String result = githubValidator.truncateUsernameForLogging(username);
        assertEquals(username, result);
    }

    @Test
    void truncateUsernameForLogging_WhenUsernameLongerThanMaxLoggingLength() {
        String longUsername = "a".repeat(usernameMaxLengthForLogging + 1);
        String expected = longUsername.substring(0, usernameMaxLengthForLogging) + "...";
        String result = githubValidator.truncateUsernameForLogging(longUsername);
        assertEquals(expected, result);
    }

}