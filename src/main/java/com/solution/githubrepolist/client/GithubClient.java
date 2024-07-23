package com.solution.githubrepolist.client;

import com.solution.githubrepolist.exception.ExternalApiException;
import com.solution.githubrepolist.exception.GithubForbiddenException;
import com.solution.githubrepolist.exception.GithubNotFoundException;
import com.solution.githubrepolist.model.dto.GithubBranchDto;
import com.solution.githubrepolist.model.externalapi.github.GithubBranchModel;
import com.solution.githubrepolist.model.dto.GithubRepositoryDto;
import com.solution.githubrepolist.model.externalapi.github.GithubRepositoryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Component
public class GithubClient extends ApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(GithubClient.class);
    private static final String BASE_URL = "https://api.github.com";
    private static final String REPOS_URL_TEMPLATE = BASE_URL + "/users/{username}/repos";
    private static final String BRANCHES_URL_TEMPLATE = BASE_URL + "/repos/{username}/{repoName}/branches";

    @Value("${github.access-token:}")
    private String bearerToken;
    @Value("${github.x-api-version-header:}")
    private String xGitHubApiVersionHeader;

    public Flux<GithubBranchDto> getBranchesForUserRepos(String username, String repoName) {
        return this.getFlux(GithubBranchModel.class, getHeaders(), BRANCHES_URL_TEMPLATE, username, repoName)
                .map(GithubBranchDto::toDto);
    }

    public Flux<GithubRepositoryDto> getRepositoriesForUser(String username) {
        return this.getFlux(GithubRepositoryModel.class, getHeaders(), REPOS_URL_TEMPLATE, username)
                .map(GithubRepositoryDto::toDto);
    }

    @Override
    protected Mono<? extends RuntimeException> handle4xxErrors(ClientResponse clientResponse) {
        if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
            LOGGER.error("Given Github account was not found, request: {} {}",
                    clientResponse.request().getMethod(),
                    clientResponse.request().getURI());
            return Mono.error(new GithubNotFoundException("Given Github account was not found"));
        } else if (clientResponse.statusCode().equals(HttpStatus.FORBIDDEN)) {
            LOGGER.error("Github API returned 'Forbidden', request: {} {}",
                    clientResponse.request().getMethod(),
                    clientResponse.request().getURI());
            return Mono.error(new GithubForbiddenException("Github API returned FORBIDDEN"));
        } else {
            LOGGER.error("Unknown Github client error on request: {} {}",
                    clientResponse.request().getMethod(),
                    clientResponse.request().getURI());
            return Mono.error(new ExternalApiException("Unknown Github API error occurred"));
        }
    }

    protected Consumer<HttpHeaders> getHeaders() {
        return (httpHeaders -> {
            httpHeaders.add(HttpHeaders.AUTHORIZATION, bearerToken);
            httpHeaders.add(HttpHeaders.ACCEPT, "application/vnd.github+json");
            httpHeaders.add("X-GitHub-Api-Version", xGitHubApiVersionHeader);
        });
    }
}
