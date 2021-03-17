package com.example.community.controller;

import com.example.community.dto.PageDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import java.util.List;

import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                    Model model,
                    @RequestParam(name = "page",defaultValue = "1") Integer page,
                    @RequestParam(name = "size",defaultValue = "8") Integer size){
        Cookie[] cookies = request.getCookies();
        String token = null;
        if(cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        User user = userMapper.findByToken(token);
        if(user != null){
            request.getSession().setAttribute("user",user);
        }
        if(page <= 0) page = 1;
        PageDTO pageDTOS = questionService.list(page,size);
        model.addAttribute("pageDTOS",pageDTOS);
        return "index";
    }

}
