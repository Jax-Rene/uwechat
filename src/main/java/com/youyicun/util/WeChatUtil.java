package com.youyicun.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.youyicun.menu.Button;
import com.youyicun.menu.ClickButton;
import com.youyicun.menu.Menu;
import com.youyicun.menu.ViewButton;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by johnny on 16/4/4.
 */
public class WeChatUtil {
    private static final String AppId = "wxfcb36acfc91399b9";
    private static final String AppSecret = "83605d826fd687c8468fe17750891fb7";
    private static final String client_credential_get = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String create_menu_url = "https://api.weixin.qq.com/cgi-bin/com.youyicun.bean.menu/create?access_token=ACCESS_TOKEN";
    private static ObjectMapper mapper = new ObjectMapper();
    private static final String token = "zdSV9peR2QvwqusoOmwoeC2WxhVMQgCv9WsBbXe1T-7al02XxRseUmBQpzDOOdMlw9Ex0J7vF88AmmKKRdAYZi60exW-dH1VIlPOaN48xML_tcR9UyEwNIjQRTFBrOu3JZKeADAQGR";


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

    public static Map<String,Object> doPost(String url,String outStr){
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


    public static Menu initMenu(){
        Menu menu = new Menu();
        ClickButton clickButton = new ClickButton();
        clickButton.setType("click");
        clickButton.setName("点击");
        clickButton.setKey("click");
        ViewButton viewButton = new ViewButton();
        viewButton.setName("那年");
        viewButton.setType("view");
        viewButton.setUrl("http://zhuangjingyang.com/chendonglin/iloveu.html");
        menu.setButton(new Button[]{clickButton,viewButton});
        return menu;
    }

    public static int createMenu(String menu){
        System.out.println(menu);
        int result = 0;
        String url = create_menu_url.replace("ACCESS_TOKEN",token);
        Map<String,Object> map = doPost(url,menu);
        if(map!=null)
            result = (int) map.get("errcode");
        return result;
    }

    public static void main(String[] args) throws IOException {
        Menu menu = initMenu();
        StringWriter stringWriter = new StringWriter();
        mapper.writeValue(stringWriter,menu);
        int res = createMenu(stringWriter.toString());
        if(res == 0){
            System.out.println("创建成功!");
        }
//        String s = client_credential_get.replace("APPID",AppId).replace("APPSECRET",AppSecret);
//        System.out.println(doGet(s));
    }
}
