package com.example.community.model;

import lombok.Data;

@Data
public class Question{

    private int id;
    private String title;
    private String description;
    private String tags;
    private Long gmt_create;
    private Long gmt_modified;
    private int creator;
    private int view_count;
    private int comment_count;
    private int like_count;

}
