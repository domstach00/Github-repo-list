package com.solution.githubrepolist.model.dto;

import com.solution.githubrepolist.model.externalapi.github.GithubOwnerModel;

import java.io.Serializable;
import java.util.Objects;

public class GithubOwnerDto implements Serializable {
    String login;

    public GithubOwnerDto() {
    }

    public GithubOwnerDto(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GithubOwnerDto ownerDto = (GithubOwnerDto) o;
        return Objects.equals(login, ownerDto.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    public static GithubOwnerDto toDto(GithubOwnerModel model) {
        GithubOwnerDto dto = new GithubOwnerDto();
        dto.setLogin(model.getLogin());
        return dto;
    }
}
