package com.youyicun.wechat.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by johnny on 16/4/21.
 */
public class UserUtil {
    public static ObjectMapper mapper = new ObjectMapper();

    private static final String OPENID_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    private static final String ALL_USER = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
    public static Map<String,Object> loadUserInfo(String openId) throws IOException {
        String accessToken = AccessTokenUtil.getToken();
        String url = OPENID_USER_INFO.replace("ACCESS_TOKEN",accessToken).replace("OPENID",openId);
        Map<String,Object> map = mapper.readValue(HttpClientUtil.doGet(url),Map.class);
        return map;
    }

    public static Map<String,Object> allUser() throws IOException{
        String accessToken = AccessTokenUtil.getToken();
        String url = ALL_USER.replace("ACCESS_TOKEN",accessToken);
        Map<String,Object> map = mapper.readValue(HttpClientUtil.doGet(url),Map.class);
        return map;
    }
}
