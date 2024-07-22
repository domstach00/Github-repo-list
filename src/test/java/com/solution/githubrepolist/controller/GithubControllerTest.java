package com.solution.githubrepolist.controller;

import com.solution.githubrepolist.exception.GithubNotFoundException;
import com.solution.githubrepolist.model.dto.GithubBranchDto;
import com.solution.githubrepolist.model.response.GithubRepositoryResponse;
import com.solution.githubrepolist.service.GithubService;
import com.solution.githubrepolist.service.GithubValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GithubControllerTest {

    @InjectMocks
    private GithubController githubController;

    @Mock
    private GithubService githubService;

    @Mock
    private GithubValidator githubValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRepositoriesWithBranchesForUser() {
        String username = "username";
        GithubBranchDto branchDto1 = new GithubBranchDto("branch1", "sha1");
        GithubBranchDto branchDto2 = new GithubBranchDto("branch2", "sha2");
        GithubBranchDto branchDto3 = new GithubBranchDto("branch3", "sha3");
        GithubBranchDto branchDto4 = new GithubBranchDto("branch4", "sha4");
        GithubRepositoryResponse repo1 =  new GithubRepositoryResponse("repoName1", "owner1", List.of(branchDto1));
        GithubRepositoryResponse repo2 = new GithubRepositoryResponse("repoName1", "owner2", List.of(branchDto2));
        GithubRepositoryResponse repo3 = new GithubRepositoryResponse("repoName1", "owner3", List.of(branchDto3, branchDto4));

        when(githubValidator.truncateUsernameForLogging(username)).thenReturn(username);
        when(githubService.getReposWithBranchesForUser(username)).thenReturn(Flux.just(repo1, repo2, repo3));

        Flux<GithubRepositoryResponse> result = githubController.getRepositoriesWithBranchesForUser(username);

        StepVerifier.create(result)
                .assertNext(response -> assertEquals(response, repo1))
                .assertNext(response -> assertEquals(response, repo2))
                .assertNext(response -> assertEquals(response, repo3))
                .verifyComplete();
    }

    @Test
    void getRepositoriesWithBranchesForUser_WhenEmpty() {
        String username = "username";

        when(githubValidator.truncateUsernameForLogging(username)).thenReturn(username);
        when(githubService.getReposWithBranchesForUser(username)).thenReturn(Flux.empty());

        Flux<GithubRepositoryResponse> result = githubController.getRepositoriesWithBranchesForUser(username);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void getRepositoriesWithBranchesForUser_WhenGithubNotFoundException() {
        String username = "username";
        String errorMsg = "Github API error";

        when(githubValidator.truncateUsernameForLogging(username)).thenReturn(username);
        when(githubService.getReposWithBranchesForUser(username)).thenReturn(Flux.error(new GithubNotFoundException(errorMsg)));

        Flux<GithubRepositoryResponse> result = githubController.getRepositoriesWithBranchesForUser(username);


        StepVerifier.create(result)
                .expectError(GithubNotFoundException.class)
                .verify();
    }

}