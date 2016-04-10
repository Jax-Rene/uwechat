package com.youyicun.wechat.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by johnny on 16/4/8.
 */
public class HttpClientUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String doGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String,Object> doPost(String url, String outStr){
        Map<String,Object> result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
        try(CloseableHttpResponse response = httpClient.execute(httpPost)){
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity,"UTF-8");
            result = mapper.readValue(s,Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
