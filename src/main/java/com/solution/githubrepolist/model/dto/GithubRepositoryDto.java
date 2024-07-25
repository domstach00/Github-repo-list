package com.solution.githubrepolist.model.dto;

import com.solution.githubrepolist.model.externalapi.github.GithubRepositoryModel;

import java.io.Serializable;

public record GithubRepositoryDto(String repositoryName, GithubOwnerDto owner, boolean isFork) implements Serializable {
    public static GithubRepositoryDto toDto(GithubRepositoryModel model) {
        return model != null
                ? new GithubRepositoryDto(model.name(), GithubOwnerDto.toDto(model.owner()), model.fork())
                : null;
    }
}
