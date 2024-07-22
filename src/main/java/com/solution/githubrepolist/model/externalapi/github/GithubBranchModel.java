package com.solution.githubrepolist.model.externalapi.github;

public class GithubBranchModel {
    private String name;
    private GithubCommitModel commit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GithubCommitModel getCommit() {
        return commit;
    }

    public void setCommit(GithubCommitModel commit) {
        this.commit = commit;
    }

}
