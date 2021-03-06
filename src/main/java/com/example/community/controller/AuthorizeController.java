package com.example.community.controller;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GithubUser;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.model.UserExample;
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
import java.util.List;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client_Id}")
    private String client_id;
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
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser != null){
            //登录成功，写cookie和Session,并将用户信息持久化存储]
            User user ;
            String account_id = String.valueOf(githubUser.getId());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(account_id);
            List<User> res;
            if((res = userMapper.selectByExample(userExample)).size() == 0){
                user = new User();
                user.setAccountId(account_id);
                user.setName(githubUser.getLogin());
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(System.currentTimeMillis());
                user.setBio(githubUser.getBio());
                user.setAvatarUrl(githubUser.getAvatarUrl());
                //这里需要刷新token，保证安全，此处进行了省略
                userMapper.insert(user);
            }else{
                user = res.get(0);
            }
            request.getSession().setAttribute("user",user);
            response.addCookie(new Cookie("token",user.getToken()));
        }else{
            //登录失败，重新登录
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
