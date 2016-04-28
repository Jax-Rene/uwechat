package com.youyicun.controller;

import com.youyicun.entity.Message;
import com.youyicun.entity.Order;
import com.youyicun.service.MessageService;
import com.youyicun.service.OrderService;
import com.youyicun.service.UserCacheService;
import com.youyicun.util.DateUtil;
import com.youyicun.wechat.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by johnny on 16/4/12.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserCacheService userCacheService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/message")
    public String adminMessage() {
        return "admin-message";
    }

    @RequestMapping("/order")
    public String adminOrder() {
        return "admin-order";
    }

    @RequestMapping(value = "/message/load" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> load(Integer start, Integer limit) throws IOException {
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

    @RequestMapping(value = "/message/avg",method = RequestMethod.POST)
    @ResponseBody
    public BigDecimal avg(){
        return messageService.avgScore();
    }


    @RequestMapping(value = "/order/load", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> load(Integer start, Integer limit, String startTime, String endTime, String phone) throws IOException {
        if (StringUtils.isEmpty(startTime))
            startTime = "2000-01-01F00:00:00";
        else
            startTime = DateUtil.parseDateTimeToLocal(startTime);
        if (StringUtils.isEmpty(endTime))
            endTime = LocalDateTime.now().plusYears(1000).toString();
        else
            endTime = DateUtil.parseDateTimeToLocal(endTime);
        List<Order> list = orderService.load(start, limit, startTime, endTime, phone, 1);
        for (Order order : list) {
            order.setOrderTime(DateUtil.parseLocalDateTime(LocalDateTime.parse(order.getOrderTime())));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("records", list);
        map.put("totalCount", orderService.countMsgNum(startTime, endTime, phone, 1));
        return map;
    }

    @RequestMapping(value = "/order/del", method = RequestMethod.POST)
    @ResponseBody
    public boolean del(@RequestParam("ids[]") List<Integer> ids) {
        orderService.delOrder(ids);
        return true;
    }
}
