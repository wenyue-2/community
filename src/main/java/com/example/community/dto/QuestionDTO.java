package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
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
    private User user;
}
