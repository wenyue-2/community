package com.example.community.controller;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
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

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = null;
        if(cookies == null) return "index";
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                token = cookie.getValue();
                 break;
            }
        }
        User user = userMapper.findByToken(token);
        if(user != null){
            request.getSession().setAttribute("user",user);
        }
        return "index";
    }

}
