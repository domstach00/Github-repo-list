package com.solution.githubrepolist.service;

import com.solution.githubrepolist.cache.CacheHelper;
import com.solution.githubrepolist.client.GithubClient;
import com.solution.githubrepolist.model.dto.GithubBranchDto;
import com.solution.githubrepolist.model.dto.GithubRepositoryDto;
import com.solution.githubrepolist.model.response.GithubRepositoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GithubServiceImpl implements GithubService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GithubServiceImpl.class);

    @Autowired
    private GithubClient githubClient;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CacheHelper cacheHelper;

//    @Cacheable(value = "nonForkRepositories", key = "#username")
    @Override
    public Flux<GithubRepositoryDto> getNonForkRepositories(String username) {
//        LOGGER.info("Fetching non-fork repositories for user: {}", username);
//        return getGithubClient().getRepositoriesForUser(username)
//                .filter(repo -> !repo.getIsFork());
        return cacheHelper.getFromCacheOrFetch("nonForkRepositories", username, GithubRepositoryDto.class, () -> {
            LOGGER.info("Fetching non-fork repositories for user: {}", username);
            return githubClient.getRepositoriesForUser(username)
                    .filter(repo -> !repo.getIsFork())
                    .doOnNext(repo -> LOGGER.info("Repository fetched: {}", repo.getRepositoryName()));
        });
    }

//    @Cacheable(value = "branchesForRepo", key = "{#username, #repoName}")
    @Override
    public Flux<GithubBranchDto> getBranchesForRepo(String username, String repoName) {
//        LOGGER.info("Fetching branches for repository: {} of user: {}", repoName, username);
//        return getGithubClient().getBranchesForUserRepos(username, repoName);
        String cacheKey = cacheHelper.createKeyFromGivenArgs(username, repoName);
        return cacheHelper.getFromCacheOrFetch("branchesForRepo", cacheKey, GithubBranchDto.class, () -> {
            LOGGER.info("Fetching branches for repository: {} of user: {}", repoName, username);
            return githubClient.getBranchesForUserRepos(username, repoName)
                    .doOnNext(branch -> LOGGER.info("Branch fetched: {}", branch.getBranchName()));
        });
    }

//    @Cacheable(value = "reposWithBranchesForUser", key = "#username")
    @Override
    public Flux<GithubRepositoryResponse> getReposWithBranchesForUser(String username) {
//        LOGGER.info("Fetching repositories with branches for user: {}", username);
//        return getNonForkRepositories(username)
//                .flatMap(githubRepository -> createGithubResponseWithBranches(username, githubRepository));
        return cacheHelper.getFromCacheOrFetch("reposWithBranchesForUser", username, GithubRepositoryResponse.class, () -> {
            LOGGER.info("Fetching repositories with branches for user: {}", username);
            return getNonForkRepositories(username)
                    .flatMap(githubRepository -> createGithubResponseWithBranches(username, githubRepository));
        });

    }

    private Mono<GithubRepositoryResponse> createGithubResponseWithBranches(String username, GithubRepositoryDto githubRepository) {
        return getBranchesForRepo(username, githubRepository.getRepositoryName())
                .collectList()
                .map(githubBranches -> GithubRepositoryResponse.fromDto(githubRepository, githubBranches));
    }

    protected GithubClient getGithubClient() {
        return this.githubClient;
    }
}
