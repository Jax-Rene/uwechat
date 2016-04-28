package com.youyicun.controller;

import com.youyicun.bean.Item;
import com.youyicun.bean.Message;
import com.youyicun.bean.NewsMessage;
import com.youyicun.util.FinalUtil;
import com.youyicun.util.WeChatCheckUtil;
import com.youyicun.wechat.util.AccessTokenUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youyicun.bean.TextMessage;
import com.youyicun.entity.MessageType;
import com.youyicun.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.Text;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/4/4.
 */
@Controller
public class WeChatController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String valid(String signature, String timestamp, String nonce, String echostr) {
        if (WeChatCheckUtil.check(signature, timestamp, nonce)) {
            return echostr;
        } else
            return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public String receivceMsg(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        Map<String, String> map = MessageUtil.xmlToMap(inputStream);
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");
        String event = map.get("Event");
        String res = null;
        if (event != null && event.equals("subscribe")) {
            TextMessage message = new TextMessage(fromUserName,toUserName);
            message.setContent(FinalUtil.SUBSCRIBE);
            res = MessageUtil.messageToXml(message);
        } else {
            if (msgType.equals(MessageType.MESSAGE_TEXT)) {
                NewsMessage newsMessage = new NewsMessage(fromUserName,toUserName,1);
                TextMessage textMessage = new TextMessage(fromUserName,toUserName);
                switch (content) {
                    case "1":
                        textMessage.setContent("<a href=''>点击进入预订</a>");
                        res = MessageUtil.messageToXml(textMessage);
                        break;
                    case "2":
                        textMessage.setContent("<a href=''>点击进入团购</a>");
                        res = MessageUtil.messageToXml(textMessage);
                        break;
                    case "3":
                        //招聘
                        Map<String,Object> jobMap = AccessTokenUtil.getPermanentMaterial("AL6iOzTbWiY_Z0tAnv5exywjhMjqVFO25MbySx53t0Y");
                        res = NewsMessageToXml(newsMessage,jobMap);
                        break;
                    //wifi
                    case "4":

                        break;
                    case "5":
                        //酒店概况
                        Map<String,Object> introduceMap = AccessTokenUtil.getPermanentMaterial("AL6iOzTbWiY_Z0tAnv5ex5X4RZnEPenuMRETp4uWpT8");
                        res = NewsMessageToXml(newsMessage,introduceMap);
                        break;
                    default:
                        textMessage.setContent(FinalUtil.DEFAULT_TEXT_MESSAGE);
                        res = MessageUtil.messageToXml(textMessage);
                }
            }
        }
        return res;
    }

    public String NewsMessageToXml(NewsMessage message,Map<String,Object> map){
        map = ((List<Map<String, Object>>) map.get("news_item")).get(0);
        Item item = new Item((String)map.get("title"),(String)map.get("digest"),(String)map
                .get("thumb_url"),(String)map.get("url"));
        List<Item> list = new ArrayList<>();
        list.add(item);
        message.setArticles(list);
        return MessageUtil.messageToXml(message);
    }
}
