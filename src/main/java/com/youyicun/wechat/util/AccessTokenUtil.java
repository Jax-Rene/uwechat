package com.youyicun.wechat.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by johnny on 16/4/8.
 */
public class AccessTokenUtil {
    private static ObjectMapper mapper = new ObjectMapper();
    private static final String APPID = "wxbcb7908a2a4a0cb0";
    private static final String APPSECRET = "388a3fbbf105b12affb6809f4160cc51";
    private static final String CLIENT_CREDENTIAL_GET = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static String token = null;
    private static Long expired = 0L;

    public static String getToken() throws IOException {
        if (token == null || System.currentTimeMillis() > expired) {
            String url = CLIENT_CREDENTIAL_GET.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
            Map<String,Object> map = mapper.readValue(HttpClientUtil.doGet(url),Map.class);
            token = (String) map.get("access_token");
            expired = System.currentTimeMillis() + (Integer)map.get("expires_in");
        }
        return token;
    }
}
