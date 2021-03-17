package com.example.community.service;

import com.example.community.dto.PageDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class QuestionService{

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageDTO list(Integer currentPage, Integer size) {
        Integer offset = size * (currentPage-1);
        PageDTO pageDTO = new PageDTO();
        List<Question> list = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : list){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOList);
        Integer totalCount = questionMapper.count();
        pageDTO.setPage(totalCount,currentPage,size);
        return pageDTO;
    }
}
