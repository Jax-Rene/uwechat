package com.youyicun.controller;

import com.youyicun.bean.Item;
import com.youyicun.bean.Message;
import com.youyicun.bean.NewsMessage;
import com.youyicun.framework.config.CryptoUtil;
import com.youyicun.util.FinalUtil;
import com.youyicun.util.WeChatCheckUtil;
import com.youyicun.wechat.util.AccessTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatController.class);

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
        if (event != null) {
            NewsMessage newsMessage = new NewsMessage(fromUserName, toUserName, 1);
            TextMessage message = new TextMessage(fromUserName, toUserName);
            switch (event) {
                case "subscribe":
                    message.setContent(FinalUtil.SUBSCRIBE);
                    res = MessageUtil.messageToXml(message);
                    break;
                case "CLICK":
                    String eventKey = map.get("EventKey");
                    Map<String,Object> clickMap = null;
                    switch (eventKey) {
                        case "join":
                            clickMap = AccessTokenUtil.getPermanentMaterial("AL6iOzTbWiY_Z0tAnv5exywjhMjqVFO25MbySx53t0Y");
                            break;
                        case "introduce":
                            clickMap = AccessTokenUtil.getPermanentMaterial("AL6iOzTbWiY_Z0tAnv5ex5X4RZnEPenuMRETp4uWpT8");
                            break;
                        case "specialOffer":
                            clickMap = AccessTokenUtil.getPermanentMaterial("AL6iOzTbWiY_Z0tAnv5ex9Oq6riCU3CDV0pe2Vzrvgk");
                            break;
                        case "lastVeg":
                            clickMap = AccessTokenUtil.getPermanentMaterial("AL6iOzTbWiY_Z0tAnv5ex9B_pXhRVERTtra_RNcBjd4");
                            break;
                    }
                    res = NewsMessageToXml(newsMessage, clickMap);
            }
        } else {
            if (msgType.equals(MessageType.MESSAGE_TEXT)) {
                NewsMessage newsMessage = new NewsMessage(fromUserName, toUserName, 1);
                TextMessage textMessage = new TextMessage(fromUserName, toUserName);
                switch (content) {
                    case "1":
                        textMessage.setContent("<a href='" + FinalUtil.ABSOLUTEPATH + "/order" + "'>点击进入预订</a>");
                        res = MessageUtil.messageToXml(textMessage);
                        break;
                    case "2":
                        textMessage.setContent("<a href=''>点击进入团购</a>");
                        res = MessageUtil.messageToXml(textMessage);
                        break;
                    case "3":
                        //招聘
                        Map<String, Object> jobMap = AccessTokenUtil.getPermanentMaterial("AL6iOzTbWiY_Z0tAnv5exywjhMjqVFO25MbySx53t0Y");
                        res = NewsMessageToXml(newsMessage, jobMap);
                        break;
                    //wifi
                    case "4":
                        textMessage.setContent(FinalUtil.WIFI);
                        res = MessageUtil.messageToXml(textMessage);
                        break;
                    case "5":
                        //酒店概况
                        Map<String, Object> introduceMap = AccessTokenUtil.getPermanentMaterial("AL6iOzTbWiY_Z0tAnv5ex5X4RZnEPenuMRETp4uWpT8");
                        res = NewsMessageToXml(newsMessage, introduceMap);
                        break;
                    default:
                        textMessage.setContent(FinalUtil.DEFAULT_TEXT_MESSAGE);
                        res = MessageUtil.messageToXml(textMessage);
                }
            }
        }
        LOGGER.info("message return res: " + res);
        return res;
    }


    public String NewsMessageToXml(NewsMessage message, Map<String, Object> map) {
        map = ((List<Map<String, Object>>) map.get("news_item")).get(0);
        Item item = new Item((String) map.get("title"), (String) map.get("digest"), (String) map
                .get("thumb_url"), (String) map.get("url"));
        List<Item> list = new ArrayList<>();
        list.add(item);
        message.setArticles(list);
        return MessageUtil.messageToXml(message);
    }
}
