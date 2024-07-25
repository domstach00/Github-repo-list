package com.solution.githubrepolist.model.dto;

import com.solution.githubrepolist.model.externalapi.github.GithubBranchModel;

import java.io.Serializable;

public record GithubBranchDto(String branchName, String lastCommitSha) implements Serializable {
    public static GithubBranchDto toDto(GithubBranchModel model) {
        if (model == null) {
            return null;
        }
        String branchName = model.name();
        String lastCommitSha = null;
        if (model.commit() != null) {
            lastCommitSha = model.commit().sha();
        }
        return new GithubBranchDto(branchName, lastCommitSha);
    }
}
