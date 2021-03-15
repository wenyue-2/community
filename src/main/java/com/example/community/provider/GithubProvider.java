package com.example.community.provider;

import com.alibaba.fastjson.JSON;
import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GithubProvider{

    @Value("${github_request_accessToken_url}")
    private String accessToken_url;
    @Value("${github_request_user_url}")
    private String user_url;

    //通过code获得用户access_token
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json;charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url(accessToken_url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String[] split = str.split("&");
            String accessToken = split[0].split("=")[1];
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(user_url)
                .header("Authorization","token "+accessToken)
                .build();
            try (Response response = client.newCall(request).execute()) {
                String str = response.body().string();
                return JSON.parseObject(str,GithubUser.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }


}
