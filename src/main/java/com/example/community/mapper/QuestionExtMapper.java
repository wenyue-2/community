package com.example.community.mapper;

import com.example.community.model.Question;

public interface QuestionExtMapper {

    int incView(Integer id);
    int incComment(Integer id);
}
