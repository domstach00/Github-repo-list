package com.solution.githubrepolist.service;

import com.solution.githubrepolist.model.dto.GithubBranchDto;
import com.solution.githubrepolist.model.dto.GithubRepositoryDto;
import com.solution.githubrepolist.model.response.GithubRepositoryResponse;
import reactor.core.publisher.Flux;

public interface GithubService {
    Flux<GithubRepositoryDto> getNonForkRepositories(String username);
    Flux<GithubBranchDto> getBranchesForRepo(String username, String repoName);
    Flux<GithubRepositoryResponse> getReposWithBranchesForUser(String username);
}
