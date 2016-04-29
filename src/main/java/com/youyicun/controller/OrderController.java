package com.youyicun.controller;

import com.youyicun.entity.Message;
import com.youyicun.entity.Order;
import com.youyicun.service.OrderService;
import com.youyicun.util.DateUtil;
import com.youyicun.util.ValidUtil;
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
    public String submitOrder(Order order, String code) throws IOException {
        String valid = ValidUtil.validOrder(order);
        if (valid != null)
            return valid;
        Map<String, Object> map = AccessTokenUtil.getUserInfoAccess(code);
        if (map.containsKey("errcode")) {
            return "请勿重复提交!";
        }
        order.setOpenId((String) map.get("openid"));
        //判断是否success为以后扩展做准备
        order.setSuccess(1);
        orderService.submitOrder(order);
        return "success";
    }

}
