package com.youyicun.controller;

import com.youyicun.entity.Message;
import com.youyicun.service.MessageService;
import com.youyicun.service.UserCacheService;
import com.youyicun.util.DateUtil;
import com.youyicun.wechat.util.AccessTokenUtil;
import com.youyicun.wechat.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    public boolean submitMsg(Message message, String code) throws IOException {
        Map<String, Object> map = AccessTokenUtil.getUserInfoAccess(code);
        if (map.containsKey("errcode")) {
            return false;
        }
        message.setOpenId((String) map.get("openid"));
        message.setTime(LocalDateTime.now().toString());
        messageService.submitMsg(message);
        return true;
    }




}
