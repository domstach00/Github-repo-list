package com.solution.githubrepolist.model.dto;

import com.solution.githubrepolist.model.externalapi.github.GithubOwnerModel;

import java.io.Serializable;

public record GithubOwnerDto(String login) implements Serializable {
    public static GithubOwnerDto toDto(GithubOwnerModel model) {
        return model != null
                ? new GithubOwnerDto(model.login())
                : null;
    }
}
