package com.example.community.service;

import com.example.community.Exception.CustomizeErrorCode;
import com.example.community.Exception.CustomizeException;
import com.example.community.dto.PageDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionExtMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.QuestionExample;
import com.example.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class QuestionService{

    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageDTO list(Integer currentPage, Integer size) {
        Integer offset = size * (currentPage-1);
        PageDTO pageDTO = new PageDTO();
        List<Question> list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : list){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOList);
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        pageDTO.setPage(totalCount,currentPage,size);
        return pageDTO;
    }

    public PageDTO list1(Integer id, Integer currentPage, Integer size) {
        Integer offset = size * (currentPage-1);
        PageDTO pageDTO = new PageDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);
        List<Question> list = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : list){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOList);
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        pageDTO.setPage(totalCount,currentPage,size);
        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        BeanUtils.copyProperties(question,questionDTO);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            questionMapper.insert(question);
        }else{
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question,questionExample);
        }
    }

    public void incView(Integer id) {
        questionExtMapper.incView(id);
    }
}
