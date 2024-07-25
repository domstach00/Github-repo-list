package com.solution.githubrepolist.model.externalapi.github;

public record GithubRepositoryModel(String name, GithubOwnerModel owner, boolean fork) {
}
