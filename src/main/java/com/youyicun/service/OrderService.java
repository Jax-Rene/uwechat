package com.youyicun.service;

import com.youyicun.dao.BaseDao;
import com.youyicun.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by johnny on 16/4/21.
 */

@Service
public class OrderService {
    @Autowired
    private BaseDao<Order> baseDao;

    public void submitOrder(Order order){
        baseDao.save(order);
    }
}
