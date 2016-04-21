package com.youyicun.controller;
import com.youyicun.entity.Order;
import com.youyicun.service.OrderService;
import com.youyicun.wechat.util.AccessTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by johnny on 16/4/21.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    @ResponseBody
    public boolean submitOrder(Order order, String code) throws IOException {
        if(!validOrder(order))
            return false;
        Map<String,Object> map = AccessTokenUtil.getUserInfoAccess(code);
        if(map.containsKey("errcode")){
            return false;
        }
        order.setOpenId((String)map.get("openid"));
        order.setSuccess(0);
        //提交前再将success设置为0 防止恶意post
        orderService.submitOrder(order);
        return true;
    }


    public boolean validOrder(Order order){
        if(StringUtils.isEmpty(order.getName()) || order.getName().length() > 7)
            return false;
        if(order.getSex() == null)
            return false;
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(order.getPhone());
        if(StringUtils.isEmpty(order.getPhone()) || !m.matches())
            return  false;
        if(order.getPeople() == null)
            return false;
        if(StringUtils.isEmpty(order.getOrderTime()))
            return false;
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime target = LocalDateTime.parse(order.getOrderTime());
        if(current.isAfter(target))
            return false;
        return true;
    }
}
