package com.solution.githubrepolist.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public abstract class ApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);

    @Autowired
    private WebClient webClient;

    public  <T> Flux<T> getFlux(Class<T> responseType, Consumer<HttpHeaders> httpHeadersConsumer,
                                String url, Object... uriVariables) {
        LOGGER.debug("Request to: {} with uriVariables: {}", url, uriVariables);
        return webClient.get()
                .uri(url, uriVariables)
                .headers(httpHeadersConsumer)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxErrors)
                .bodyToFlux(responseType);
    }

    protected abstract Mono<? extends Throwable> handle4xxErrors(ClientResponse clientResponse);
}
