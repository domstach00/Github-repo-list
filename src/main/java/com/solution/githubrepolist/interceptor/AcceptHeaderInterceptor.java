package com.solution.githubrepolist.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AcceptHeaderInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptHeaderInterceptor.class);
    private static final String REQUIRED_ACCEPT_HEADER_VALUE = "application/json";
    private static final String ERROR_RESPONSE_MESSAGE = "Header has to contains 'Accept: " + REQUIRED_ACCEPT_HEADER_VALUE;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        if (acceptHeader == null || !acceptHeader.contains(REQUIRED_ACCEPT_HEADER_VALUE)) {
            LOGGER.warn("Invalid Accept header. Expected: {}", REQUIRED_ACCEPT_HEADER_VALUE);
            handleErrorResponse(response);
            return false;
        }
        return true;
    }

    private void handleErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(ERROR_RESPONSE_MESSAGE);
    }
}
