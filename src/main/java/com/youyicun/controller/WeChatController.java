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

        //新建返回的文字消息
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType("text");
        text.setCreateTime(new Date().getTime());

        //关注事件
        if (event != null && event.equals("subscribe")) {
            text.setContent("真诚地感谢您对惠安县螺城镇又一村酒店的关注！\n" +
                    "\n" +
                    "新浪微博：@又一村酒店\n" +
                    "\n" +
                    "酒店地址：福建省泉州市惠安县螺城镇霞张花园A幢\n" +
                    "电　　话：0595-87368378\n" +
                    "\n" +
                    "回复以下数字获取相关信息：\n" +
                    "\n" +
                    "【1】- 酒店预定\n" +
                    "【2】- 团购活动\n" +
                    "【3】- 人员招聘\n" +
                    "【4】- 关于wifi\n" +
                    "【5】-酒店概况");
        } else {
            //回复文本消息
            if (msgType.equals(MessageType.MESSAGE_TEXT)) {
                switch (content) {
                    case "1":
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    case "5":
                        break;
                    default:
                        text.setContent("回复以下数字获取相关信息：\n" +
                                "\n" +
                                "【1】- 酒店预定\n" +
                                "【2】- 团购活动\n" +
                                "【3】- 人员招聘\n" +
                                "【4】- 关于wifi\n" +
                                "【5】-酒店概况");
                }

            } else {
                text.setContent("回复以下数字获取相关信息：\n" +
                        "\n" +
                        "【1】- 酒店预定\n" +
                        "【2】- 团购活动\n" +
                        "【3】- 人员招聘\n" +
                        "【4】- 关于wifi\n" +
                        "【5】-酒店概况");
            }
        }
        String message = MessageUtil.textMessageToXml(text);
        return message;
    }

}
