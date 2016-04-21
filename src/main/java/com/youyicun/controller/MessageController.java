package com.youyicun.controller;

import com.youyicun.entity.Message;
import com.youyicun.service.MessageService;
import com.youyicun.wechat.util.AccessTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * Created by johnny on 16/4/19.
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public boolean submitMsg(Message message, String code) throws IOException {
        Map<String, Object> map = AccessTokenUtil.getUserInfoAccess(code);
        if (map.containsKey("errcode")) {
            return false;
        }
        message.setOpenId((String) map.get("openid"));
        messageService.submitMsg(message);
        return true;
    }
}
