package note.dk.community.community.provider;

import com.alibaba.fastjson.JSON;
import note.dk.community.community.dto.AccessTokenDTO;
import note.dk.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component//可以完成ioc功能 让类不用再实例化 可以直接通过注解使用
public class GithubProvider {
    //参数超过两个要封装成对象
    public String getAccessToken(AccessTokenDTO accessTokenDTO){//得到accesstoken
        MediaType mediaType= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));//JSON.toJSONString是accessTokenDTO的stringjason形式 建立一个请求体 是以accessTokenDTO为基础的请求体
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id=9fb3ccafecf07330d04a&client_secret=9b07a7b3c7958d0e57d81d4968e7613143cbc17c&code="+accessTokenDTO.getCode()+"&redirect_uri=http://localhost:8887/callback&state=1")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();//通过上面的post请求得到回应的代码段
            String string=response.body().string();//从中提取出token
            String[] split = string.split("&");
            String[] split1 = split[0].split("=");
            String  token=split1[1];//得出token
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accessToken) {//通过accessToken获得user对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string=response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//JSON.parseObject可以把string自动转为githubuser类对象
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
