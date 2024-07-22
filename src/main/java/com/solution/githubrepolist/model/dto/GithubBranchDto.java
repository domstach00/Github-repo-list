package com.solution.githubrepolist.model.dto;

import com.solution.githubrepolist.model.externalapi.github.GithubBranchModel;

import java.util.Objects;

public class GithubBranchDto {
    private String branchName;
    private String lastCommitSha;

    public GithubBranchDto() {
    }

    public GithubBranchDto(String branchName, String lastCommitSha) {
        this.branchName = branchName;
        this.lastCommitSha = lastCommitSha;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLastCommitSha() {
        return lastCommitSha;
    }

    public void setLastCommitSha(String lastCommitSha) {
        this.lastCommitSha = lastCommitSha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GithubBranchDto that = (GithubBranchDto) o;
        return Objects.equals(branchName, that.branchName) && Objects.equals(lastCommitSha, that.lastCommitSha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchName, lastCommitSha);
    }

    public static GithubBranchDto toDto(GithubBranchModel model) {
        GithubBranchDto dto = new GithubBranchDto();
        dto.setBranchName(model.getName());
        if (model.getCommit() != null) {
            dto.setLastCommitSha(model.getCommit().getSha());
        }
        return dto;
    }
}
