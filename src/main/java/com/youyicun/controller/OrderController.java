package com.youyicun.controller;

import com.youyicun.entity.Message;
import com.youyicun.entity.Order;
import com.youyicun.service.OrderService;
import com.youyicun.util.DateUtil;
import com.youyicun.wechat.util.AccessTokenUtil;
import com.youyicun.wechat.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by johnny on 16/4/21.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public boolean submitOrder(Order order, String code) throws IOException {
        if (!validOrder(order))
            return false;
        Map<String, Object> map = AccessTokenUtil.getUserInfoAccess(code);
        if (map.containsKey("errcode")) {
            return false;
        }
        order.setOpenId((String) map.get("openid"));
        order.setSuccess(0);
        //提交前再将success设置为0 防止恶意post
        orderService.submitOrder(order);
        return true;
    }


    public boolean validOrder(Order order) {
        if (StringUtils.isEmpty(order.getLastName()) || order.getLastName().length() > 7)
            return false;
        if (order.getSex() == null)
            return false;
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(order.getPhone());
        if (StringUtils.isEmpty(order.getPhone()) || !m.matches())
            return false;
        if (order.getPeople() == null)
            return false;
        if (StringUtils.isEmpty(order.getOrderTime()))
            return false;
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime target = LocalDateTime.parse(order.getOrderTime());
        if (current.isAfter(target))
            return false;
        return true;
    }


    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public Map<String, Object> load(Integer start, Integer limit, String startTime, String endTime, String phone) throws IOException {
        if (StringUtils.isEmpty(startTime))
            startTime = "2000-01-01F00:00:00";
        else
            startTime = DateUtil.parseDateTimeToLocal(startTime);
        if (StringUtils.isEmpty(endTime))
            endTime = LocalDateTime.now().toString();
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

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public boolean del(@RequestParam("ids[]") List<Integer> ids) {
        orderService.delOrder(ids);
        return true;
    }

}
