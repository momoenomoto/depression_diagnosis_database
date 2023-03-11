package com.nyu.provider;

import com.alibaba.fastjson2.JSON;
import com.nyu.dto.AccessTokenDTO;
import com.nyu.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
//mediatype.get or mediatype.parse
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();



        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try  (Response response = client.newCall(request).execute()){

            String string = response.body().string();
            return response.body().string();
        } catch (IOException e) {

        }
        return null;

    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        //RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {

        }
        return null;
    }


}