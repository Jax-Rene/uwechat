package com.youyicun.util;

import com.youyicun.entity.Order;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by johnny on 16/4/28.
 */
public class ValidUtil {
    public static String validOrder(Order order) {
        if (StringUtils.isEmpty(order.getLastName()) || order.getLastName().length() > 7)
            return "输入称呼不合法,请重新输入";
        if (order.getSex() == null)
            return "输入错误,请重新输入";
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(order.getPhone());
        if (StringUtils.isEmpty(order.getPhone()) || !m.matches())
            return "联系电话不合法,请重新输入";
        if (order.getPeople() == null || order.getPeople() == 0)
            return "用餐人数不合法,请重新输入";
        if (StringUtils.isEmpty(order.getOrderTime()))
            return "订单时间不合法,请重新输入";
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime target = LocalDateTime.parse(order.getOrderTime());
        if (current.isAfter(target))
            return "订单时间不合法,请重新输入";
        if(current.plusDays(7).isBefore(target))
            return "很抱歉,无法预约超过一星期";
        return null;
    }

}
