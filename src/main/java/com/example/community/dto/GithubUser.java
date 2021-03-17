package com.example.community.dto;

import lombok.Data;

@Data
public class GithubUser {

    private Long id;
    private String login;
    private String bio;
    private String avatar_url;
}
