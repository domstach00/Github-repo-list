package com.solution.githubrepolist.service;

import com.solution.githubrepolist.client.GithubClient;
import com.solution.githubrepolist.model.dto.GithubBranchDto;
import com.solution.githubrepolist.model.dto.GithubRepositoryDto;
import com.solution.githubrepolist.model.response.GithubRepositoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GithubServiceImpl implements GithubService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GithubServiceImpl.class);

    @Autowired
    private GithubClient githubClient;

    @Cacheable(value = "GithubServiceImpl.getNonForkRepositories", key = "#username", sync = true)
    @Override
    public Flux<GithubRepositoryDto> getNonForkRepositories(String username) {
        LOGGER.info("Fetching non-fork repositories for user: {}", username);
        return getGithubClient().getRepositoriesForUser(username)
                .filter(repo -> !repo.isFork());
    }

    @Cacheable(value = "GithubServiceImpl.getBranchesForRepo", key = "{#username, #repoName}", sync = true)
    @Override
    public Flux<GithubBranchDto> getBranchesForRepo(String username, String repoName) {
        LOGGER.info("Fetching branches for repository: {} of user: {}", repoName, username);
        return getGithubClient().getBranchesForUserRepos(username, repoName);
    }

    @Cacheable(value = "GithubServiceImpl.getReposWithBranchesForUser", key = "#username", sync = true)
    @Override
    public Flux<GithubRepositoryResponse> getReposWithBranchesForUser(String username) {
        LOGGER.info("Started fetching repositories with branches for user: {}", username);
        return getNonForkRepositories(username)
                .flatMap(githubRepository -> createGithubResponseWithBranches(username, githubRepository));
    }

    private Mono<GithubRepositoryResponse> createGithubResponseWithBranches(String username, GithubRepositoryDto githubRepository) {
        return getBranchesForRepo(username, githubRepository.repositoryName())
                .collectList()
                .map(githubBranches -> GithubRepositoryResponse.fromDto(githubRepository, githubBranches));
    }

    protected GithubClient getGithubClient() {
        return this.githubClient;
    }
}
