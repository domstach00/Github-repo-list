package com.solution.githubrepolist.model.dto;

import com.solution.githubrepolist.model.externalapi.github.GithubRepositoryModel;

import java.util.Objects;

public class GithubRepositoryDto {
    private String repositoryName;
    private boolean isFork;
    private GithubOwnerDto owner;

    public GithubRepositoryDto() {
    }

    public GithubRepositoryDto(String repositoryName, GithubOwnerDto owner, boolean isFork) {
        this.repositoryName = repositoryName;
        this.owner = owner;
        this.isFork = isFork;
    }

    public boolean getIsFork() {
        return isFork;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public void setFork(boolean fork) {
        isFork = fork;
    }

    public GithubOwnerDto getOwner() {
        return owner;
    }

    public void setOwner(GithubOwnerDto owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GithubRepositoryDto that = (GithubRepositoryDto) o;
        return isFork == that.isFork && Objects.equals(repositoryName, that.repositoryName) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repositoryName, isFork, owner);
    }

    public static GithubRepositoryDto toDto(GithubRepositoryModel model) {
        GithubRepositoryDto dto = new GithubRepositoryDto();
        dto.setRepositoryName(model.getName());
        dto.setFork(model.isFork());
        dto.setOwner(GithubOwnerDto.toDto(model.getOwner()));
        return dto;
    }
}
