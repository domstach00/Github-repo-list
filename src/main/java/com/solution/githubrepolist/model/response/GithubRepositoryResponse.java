package com.solution.githubrepolist.model.response;

import com.solution.githubrepolist.model.dto.GithubBranchDto;
import com.solution.githubrepolist.model.dto.GithubRepositoryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GithubRepositoryResponse {
    private String repositoryName;
    private String ownerLogin;
    private List<GithubBranchDto> branches;

    public GithubRepositoryResponse() {
    }

    public GithubRepositoryResponse(String repositoryName, String ownerLogin, List<GithubBranchDto> branches) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<GithubBranchDto> getBranches() {
        return branches;
    }

    public void addBranch(GithubBranchDto branchDto) {
        if (this.branches == null) {
            setBranches(new ArrayList<>());
        }
        this.branches.add(branchDto);
    }

    public void setBranches(List<GithubBranchDto> branches) {
        this.branches = branches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GithubRepositoryResponse that = (GithubRepositoryResponse) o;
        return Objects.equals(repositoryName, that.repositoryName) && Objects.equals(ownerLogin, that.ownerLogin) && Objects.equals(branches, that.branches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repositoryName, ownerLogin, branches);
    }

    public static GithubRepositoryResponse fromDto(GithubRepositoryDto githubRepository, List<GithubBranchDto> githubBranches) {
        GithubRepositoryResponse repositoryResponse = new GithubRepositoryResponse();
        repositoryResponse.setRepositoryName(githubRepository.getRepositoryName());
        if (githubRepository.getOwner() != null) {
            repositoryResponse.setOwnerLogin(githubRepository.getOwner().getLogin());
        }
        repositoryResponse.setBranches(githubBranches);
        return repositoryResponse;
    }
}
