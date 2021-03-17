package com.example.community.controller;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GithubUser;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client_id}")
    private String client_Id;
    @Value("${github.client_secret}")
    private String client_secret;
    @Value("${github.redirect_url}")
    private String redirect_url;
    @Autowired
    private UserMapper userMapper;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                            HttpServletRequest request,
                            HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirect_url);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(client_Id);
        accessTokenDTO.setClient_secret(client_secret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser != null){
            //登录成功，写cookie和Session,并将用户信息持久化存储]
            User user ;
            String account_id = String.valueOf(githubUser.getId());
            if((user = userMapper.findByAccount_id(account_id)) == null){
                user = new User();
                user.setAccount_id(account_id);
                user.setName(githubUser.getLogin());
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setGmt_create(System.currentTimeMillis());
                user.setGmt_modified(System.currentTimeMillis());
                user.setBio(githubUser.getBio());
                user.setAvatar_url(githubUser.getAvatar_url());
                userMapper.insert(user);
            }
            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:/";
        }else{
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
