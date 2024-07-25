package com.solution.githubrepolist.model.response;

import com.solution.githubrepolist.model.dto.GithubBranchDto;
import com.solution.githubrepolist.model.dto.GithubRepositoryDto;

import java.io.Serializable;
import java.util.List;

public record GithubRepositoryResponse(String repositoryName, String ownerLogin, List<GithubBranchDto> branches) implements Serializable {
    public static GithubRepositoryResponse fromDto(GithubRepositoryDto githubRepository, List<GithubBranchDto> githubBranches) {
        String repositoryName = githubRepository.repositoryName();
        String ownerLogin = null;
        if (githubRepository.owner() != null) {
            ownerLogin = githubRepository.owner().login();
        }
        return new GithubRepositoryResponse(repositoryName, ownerLogin, githubBranches);
    }
}
