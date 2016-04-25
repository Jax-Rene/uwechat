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
    @Autowired
    private UserCacheService userCacheService;

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

    @RequestMapping(value = "/load" , method = RequestMethod.POST)
    public Map<String,Object> load(Integer start,Integer limit) throws IOException {
        Map<String,Object> map = new HashMap<>();
        List<Message> list = messageService.load(start,limit);
        Map<String,Object> users = userCacheService.getUser();
        for(Message s:list){
            if(users.get(s.getOpenId()) == null) {
                Map<String, Object> user = UserUtil.loadUserInfo(s.getOpenId());
                s.setNickName((String) user.get("nickname"));
            }else{
                s.setNickName((String) ((Map<String,Object>)users.get(s.getOpenId())).get("nickname"));
            }
            s.setTime(DateUtil.parseLocalDateTime(LocalDateTime.parse(s.getTime())));
        }
        map.put("records",list);
        map.put("totalCount",messageService.countMsgNum());
        map.put("avg",messageService.avgScore());
        return map;
    }

    @RequestMapping(value = "/avg",method = RequestMethod.POST)
    public BigDecimal avg(){
        return messageService.avgScore();
    }



}
