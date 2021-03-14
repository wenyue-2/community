package com.example.community.dto;

public class GithubUser {

    private Long id;
    private String login;
    private String bio;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "GithubUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
