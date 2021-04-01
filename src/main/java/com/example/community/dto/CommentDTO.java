package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {

    private User user;
    private Integer id;
    private Integer parentId;
    private Integer type;
    private String content;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;

}
