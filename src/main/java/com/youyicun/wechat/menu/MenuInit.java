package com.youyicun.wechat.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youyicun.util.FinalUtil;
import com.youyicun.wechat.util.AccessTokenUtil;
import com.youyicun.wechat.util.HttpClientUtil;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by johnny on 16/4/8.
 */
public class MenuInit {
    private static ObjectMapper mapper = new ObjectMapper();
    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    public static int initMenu() throws Exception {
        Menu menu = new Menu();
        Button buyButton = new Button("订购");
        ViewButton bookButton = new ViewButton("view", "酒店预订", generateUrl(FinalUtil.ABSOLUTEPATH + "/order"));
        ViewButton groupButton = new ViewButton("view", "团购", generateUrl(FinalUtil.ABSOLUTEPATH + "/order"));
        buyButton.setSub_button(new Button[]{bookButton,groupButton});

        ClickButton freeActiveButton = new ClickButton("click","优惠活动","specialOffer");


        Button usButton = new Button("资讯");
        ViewButton messageButton = new ViewButton("view", "评价留言", generateUrl(FinalUtil.ABSOLUTEPATH + "/message"));
        ClickButton vegButton = new ClickButton("click", "最新菜品", "lastVeg");
        ClickButton introduceButton = new ClickButton("click", "酒店概况", "introduce");
        ClickButton joinButton = new ClickButton("click", "求职招聘", "join");
        ViewButton contactButton = new ViewButton("view", "联系我们", "http://mp.weixin.qq.com/s?__biz=MzI4OTIyMjIzNw==&mid=100000010&idx=1&sn=dc74d2a02d8823a2880bba59a33e1a50#rd");
        usButton.setSub_button(new Button[]{messageButton, vegButton,introduceButton,joinButton,contactButton});

        menu.setButton(new Button[]{buyButton,freeActiveButton,usButton});
        StringWriter stringWriter = new StringWriter();
        mapper.writeValue(stringWriter, menu);
        return createMenu(stringWriter.toString());
    }

    public static int createMenu(String menu) throws Exception{
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", AccessTokenUtil.getToken());
        System.out.println(url);
        Map<String,Object> map = HttpClientUtil.doPost(url,menu);
        if(map!=null)
            result = (int) map.get("errcode");
        return result;
    }


    public static String generateUrl(String url) throws IOException{
        String res = "https://open.weixin.qq.com/connect/oauth2/authorize?";
        res += "appid=" + AccessTokenUtil.APPID + "&redirect_uri=" +  URLEncoder.encode(url,"utf-8") +
                "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        System.out.println(res);
        return res;
    }

    public static void main(String[] args) throws Exception {
        initMenu();
    }
}