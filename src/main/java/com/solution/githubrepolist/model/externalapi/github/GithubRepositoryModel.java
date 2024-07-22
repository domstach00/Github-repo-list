package com.solution.githubrepolist.model.externalapi.github;

public class GithubRepositoryModel {
    private String name;
    private GithubOwnerModel owner;
    private boolean fork;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public GithubOwnerModel getOwner() {
        return owner;
    }

    public void setOwner(GithubOwnerModel owner) {
        this.owner = owner;
    }
}
