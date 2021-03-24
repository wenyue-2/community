package com.example.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {

    private String clientId;
    private String clientSecret;
    private String code;
    private String redirectUrl;
    private String state;

}
