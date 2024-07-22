package com.solution.githubrepolist.controller;

import com.solution.githubrepolist.service.GithubService;
import com.solution.githubrepolist.model.response.GithubRepositoryResponse;
import com.solution.githubrepolist.service.GithubValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController()
@RequestMapping("/github/repos")
public class GithubController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GithubController.class);

    @Autowired
    private GithubService githubService;
    @Autowired
    private GithubValidator githubValidator;

    @GetMapping("/non-fork/")
    public Flux<GithubRepositoryResponse> getRepositoriesWithBranchesForUser(@RequestParam String username) {
        LOGGER.info("Received request to get Github repositories with branches for user: {}",
                getGithubValidator().truncateUsernameForLogging(username));
        getGithubValidator().validateUsername(username);
        return getGithubService().getReposWithBranchesForUser(username);
    }

    protected GithubService getGithubService() {
        return this.githubService;
    }

    protected GithubValidator getGithubValidator() {
        return this.githubValidator;
    }

}
