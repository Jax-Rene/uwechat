package com.youyicun.wechat.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youyicun.wechat.util.AccessTokenUtil;
import com.youyicun.wechat.util.HttpClientUtil;
import java.io.StringWriter;
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
        ViewButton bookButton = new ViewButton("view", "酒店预订", "https://zhuangjingyang.pagekite.me/book");
        ViewButton groupButton = new ViewButton("view", "团购", "https://zhuangjingyang.pagekite.me/book");
        buyButton.setSub_button(new Button[]{bookButton,groupButton});

        Button freeButton = new Button("优惠便捷");
        ViewButton freeActiveButton = new ViewButton("view", "优惠活动", "https://zhuangjingyang.pagekite.me/book");
        ViewButton wifiButton = new ViewButton("view", "一键上网", "https://zhuangjingyang.pagekite.me/book");
        ViewButton vegButton = new ViewButton("view", "最新菜品", "https://zhuangjingyang.pagekite.me/book");
        freeButton.setSub_button(new Button[]{freeActiveButton,wifiButton,vegButton});

        Button usButton = new Button("联系我们");
        ViewButton messageButton = new ViewButton("view", "评价留言", "https://zhuangjingyang.pagekite.me/book");
        ViewButton introduceButton = new ViewButton("view", "酒店概况", "https://zhuangjingyang.pagekite.me/book");
        ViewButton joinButton = new ViewButton("view", "加入我们", "https://zhuangjingyang.pagekite.me/book");
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

    public static void main(String[] args) throws Exception {
        initMenu();
    }
}