package com.solution.githubrepolist.service;

import com.solution.githubrepolist.client.GithubClient;
import com.solution.githubrepolist.model.dto.GithubBranchDto;
import com.solution.githubrepolist.model.dto.GithubOwnerDto;
import com.solution.githubrepolist.model.dto.GithubRepositoryDto;
import com.solution.githubrepolist.model.response.GithubRepositoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GithubServiceImplTest {

    @Mock
    private GithubClient githubClient;

    @InjectMocks
    private GithubServiceImpl githubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getNonForkRepositories() {
        String username = "testuser";
        GithubOwnerDto owner = new GithubOwnerDto(username);
        GithubRepositoryDto repo1 = new GithubRepositoryDto("repo1", owner, false);
        GithubRepositoryDto repo2 = new GithubRepositoryDto("repo2", owner, true);
        GithubRepositoryDto repo3 = new GithubRepositoryDto("repo3", owner, false);

        when(githubClient.getRepositoriesForUser(username))
                .thenReturn(Flux.just(repo1, repo2, repo3));

        Flux<GithubRepositoryDto> result = githubService.getNonForkRepositories(username);

        StepVerifier.create(result)
                .expectNext(repo1)
                .expectNext(repo3)
                .verifyComplete();
    }

    @Test
    void getNonForkRepositories_WhenNoRepos() {
        String username = "testuser";

        when(githubClient.getRepositoriesForUser(username))
                .thenReturn(Flux.empty());

        Flux<GithubRepositoryDto> result = githubService.getNonForkRepositories(username);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void getNonForkRepositories_WhenOnlyForkRepos() {
        String username = "testuser";
        GithubOwnerDto owner = new GithubOwnerDto(username);
        GithubRepositoryDto repo1 = new GithubRepositoryDto("repo1", owner, true);
        GithubRepositoryDto repo2 = new GithubRepositoryDto("repo2", owner, true);
        GithubRepositoryDto repo3 = new GithubRepositoryDto("repo3", owner, true);

        when(githubClient.getRepositoriesForUser(username))
                .thenReturn(Flux.just(repo1, repo2, repo3));

        Flux<GithubRepositoryDto> result = githubService.getNonForkRepositories(username);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void getNonForkRepositories_WhenClientError() {
        String username = "testuser";
        String errorMsg = "GitHub API error";

        when(githubClient.getRepositoriesForUser(username))
                .thenReturn(Flux.error(new RuntimeException(errorMsg)));

        Flux<GithubRepositoryDto> result = githubService.getNonForkRepositories(username);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void getBranchesForRepo() {
        String username = "testuser";
        String repoName = "repo1";
        GithubBranchDto branch1 = new GithubBranchDto("branch1", "sha1");
        GithubBranchDto branch2 = new GithubBranchDto("branch2", "sha2");

        when(githubClient.getBranchesForUserRepos(username, repoName))
                .thenReturn(Flux.just(branch1, branch2));

        Flux<GithubBranchDto> result = githubService.getBranchesForRepo(username, repoName);

        StepVerifier.create(result)
                .expectNext(branch1)
                .expectNext(branch2)
                .verifyComplete();
    }

    @Test
    void getBranchesForRepo_WhenEmpty() {
        String username = "testuser";
        String repoName = "repo1";

        when(githubClient.getBranchesForUserRepos(username, repoName))
                .thenReturn(Flux.empty());

        Flux<GithubBranchDto> result = githubService.getBranchesForRepo(username, repoName);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void getBranchesForRepo_WhenClientError() {
        String username = "testuser";
        String repoName = "repo1";
        String errorMsg = "GitHub API error";

        when(githubClient.getBranchesForUserRepos(username, repoName))
                .thenReturn(Flux.error(new RuntimeException(errorMsg)));

        Flux<GithubBranchDto> result = githubService.getBranchesForRepo(username, repoName);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void getReposWithBranchesForUser() {
        String username = "testuser";
        String repoName = "repo1";
        String branchName = "branch1";
        GithubOwnerDto owner = new GithubOwnerDto(username);
        GithubRepositoryDto repo1 = new GithubRepositoryDto(repoName, owner, false);
        GithubBranchDto branch1 = new GithubBranchDto(branchName, "sha1");

        when(githubClient.getRepositoriesForUser(username))
                .thenReturn(Flux.just(repo1));
        when(githubClient.getBranchesForUserRepos(username, "repo1"))
                .thenReturn(Flux.just(branch1));

        Flux<GithubRepositoryResponse> result = githubService.getReposWithBranchesForUser(username);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertEquals(response.repositoryName(), repoName);
                    assertEquals(response.branches().size(), 1);
                    assertEquals(response.branches().get(0).branchName(), branchName);
                })
                .verifyComplete();
    }

    @Test
    void getReposWithBranchesForUser_WhenEmptyRepositories() {
        String username = "testuser";

        when(githubClient.getRepositoriesForUser(username))
                .thenReturn(Flux.empty());

        Flux<GithubRepositoryResponse> result = githubService.getReposWithBranchesForUser(username);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void getReposWithBranchesForUser_WhenEmptyBranches() {
        String username = "testuser";
        GithubOwnerDto owner = new GithubOwnerDto(username);
        String repoName = "repo1";
        GithubRepositoryDto repo1 = new GithubRepositoryDto(repoName, owner, false);

        when(githubClient.getRepositoriesForUser(username))
                .thenReturn(Flux.just(repo1));
        when(githubClient.getBranchesForUserRepos(username, repoName))
                .thenReturn(Flux.empty());

        Flux<GithubRepositoryResponse> result = githubService.getReposWithBranchesForUser(username);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertEquals(response.repositoryName(), repoName);
                    assertEquals(response.branches().size(), 0);
                })
                .verifyComplete();
    }

    @Test
    void getReposWithBranchesForUser_WhenClientError() {
        String username = "testuser";
        String errorMsg = "GitHub API error";

        when(githubClient.getRepositoriesForUser(username))
                .thenReturn(Flux.error(new RuntimeException(errorMsg)));

        Flux<GithubRepositoryResponse> result = githubService.getReposWithBranchesForUser(username);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }
}