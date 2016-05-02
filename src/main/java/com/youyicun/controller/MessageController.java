package com.youyicun.controller;

import com.youyicun.entity.Message;
import com.youyicun.service.MessageService;
import com.youyicun.service.OrderService;
import com.youyicun.service.UserCacheService;
import com.youyicun.util.DateUtil;
import com.youyicun.wechat.util.AccessTokenUtil;
import com.youyicun.wechat.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private MessageService messageService;


    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitMsg(Message message, String code) throws IOException {
        Map<String, Object> map = AccessTokenUtil.getUserInfoAccess(code);
        if (map.containsKey("errcode")) {
            return "您已提交成功,请勿重复提交!";
        }
        message.setOpenId((String) map.get("openid"));
        message.setTime(LocalDateTime.now().toString());
        messageService.submitMsg(message);
        LOGGER.info("commit message to database successfully id : " + message.getId());
        return "success";
    }
}
