package com.youyicun.controller;

import com.youyicun.entity.Order;
import com.youyicun.service.OrderService;
import com.youyicun.util.ValidUtil;
import com.youyicun.wechat.util.AccessTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;

/**
 * Created by johnny on 16/4/21.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitOrder(Order order, String code) throws IOException {
        String valid = ValidUtil.validOrder(order);
        if (valid != null)
            return valid;
        Map<String, Object> map = AccessTokenUtil.getUserInfoAccess(code);
        if (map.containsKey("errcode")) {
            return "您已提交成功,请勿重复提交!";
        }
        order.setOpenId((String) map.get("openid"));
        //判断是否success为以后扩展做准备
        order.setSuccess(1);
        orderService.submitOrder(order);
        LOGGER.info("add order successfully id: " + order.getId());
        return "success";
    }
}
