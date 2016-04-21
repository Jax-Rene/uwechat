package com.youyicun.wechat.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        Button buyButton = new Button("微交易");
        ViewButton bookButton = new ViewButton("view", "酒店预订", generateUrl("https://zhuangjingyang.pagekite.me/order"));
        ViewButton groupButton = new ViewButton("view", "团购", "https://zhuangjingyang.pagekite.me/book");
        buyButton.setSub_button(new Button[]{bookButton,groupButton});

        Button freeButton = new Button("优惠便捷");
        ViewButton freeActiveButton = new ViewButton("view", "优惠活动", "https://zhuangjingyang.pagekite.me/book");
        ViewButton wifiButton = new ViewButton("view", "一键上网", "https://zhuangjingyang.pagekite.me/book");
        ViewButton vegButton = new ViewButton("view", "最新菜品", "https://zhuangjingyang.pagekite.me/book");
        freeButton.setSub_button(new Button[]{freeActiveButton,wifiButton,vegButton});

        Button usButton = new Button("联系我们");
        ViewButton messageButton = new ViewButton("view", "评价留言", generateUrl("https://zhuangjingyang.pagekite.me/message"));
        ViewButton introduceButton = new ViewButton("view", "酒店概况", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbcb7908a2a4a0cb0&redirect_uri=https%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
        ViewButton joinButton = new ViewButton("view", "加入我们", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbcb7908a2a4a0cb0&redirect_uri=https%3a%2f%2fzhuangjingyang.pagekite.me%2fmessage%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
        ViewButton contactButton = new ViewButton("view", "联系方式", "https://zhuangjingyang.pagekite.me/book");
        usButton.setSub_button(new Button[]{messageButton, introduceButton,joinButton,contactButton});

        menu.setButton(new Button[]{buyButton,freeButton,usButton});
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