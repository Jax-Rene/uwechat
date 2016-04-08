package com.youyicun.controller;

import com.youyicun.util.WeChatCheckUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youyicun.bean.TextMessage;
import com.youyicun.entity.MessageType;
import com.youyicun.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 * Created by johnny on 16/4/4.
 */
@Controller
@EnableAutoConfiguration
public class WeChatController {
    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseBody
    public String valid(String signature,String timestamp,String nonce,String echostr){
        if(WeChatCheckUtil.check(signature,timestamp,nonce)){
            return echostr;
        }
        else
            return null;
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    @ResponseBody
    public String receivceMsg(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        Map<String,String> map = MessageUtil.xmlToMap(inputStream);
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");

        String message = null;
        switch (msgType){
            case MessageType.MESSAGE_TEXT:
                TextMessage text = new TextMessage();
                text.setFromUserName(toUserName);
                text.setToUserName(fromUserName);
                text.setMsgType("text");
                text.setCreateTime(new Date().getTime());
                text.setContent("您发送的消息是: " + content);
                message = MessageUtil.textMessageToXml(text);
                break;
            default:
                break;
        }
        return message;
    }

}
