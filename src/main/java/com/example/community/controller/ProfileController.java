package com.example.community.controller;

import com.example.community.dto.PageDTO;
import com.example.community.model.User;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "8") Integer size){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) return "index";
        if("questions".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
            PageDTO pageDTOS = questionService.list1(user.getId(), page, size);
            model.addAttribute("pageDTOS",pageDTOS);
        }else if("replies".equals(action)){
            model.addAttribute("section","reply");
            model.addAttribute("sectionName","我的回复");
        }

        return "profile";
    }
}
