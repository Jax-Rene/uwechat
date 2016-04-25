package com.youyicun.service;

import com.youyicun.dao.BaseDao;
import com.youyicun.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/4/21.
 */

@Service
public class OrderService {
    @Autowired
    private BaseDao<Order> baseDao;

    public void submitOrder(Order order) {
        baseDao.save(order);
    }

    public List<Order> load(Integer start, Integer limit, String startTime, String endTime, String phone, Integer success) {
        Map<String,Object> hs = new HashMap<>();
        hs.put("phone","%" + phone + "%");
        hs.put("startTime",startTime);
        hs.put("endTime",endTime);
        hs.put("success",success);
        String hql = "FROM Order WHERE phone LIKE :phone AND orderTime > :startTime AND orderTime < :endTime AND success = :success AND isDel = 0";
        return baseDao.query(hql,hs,start,limit);
    }

    public Long countMsgNum(String startTime, String endTime, String phone, Integer success) {
        Map<String,Object> hs = new HashMap<>();
        hs.put("phone","%" + phone + "%");
        hs.put("startTime",startTime);
        hs.put("endTime",endTime);
        hs.put("success",success);
        String hql = "SELECT COUNT(*) FROM Order WHERE phone LIKE :phone AND orderTime > :startTime AND orderTime < :endTime AND success = :success AND isDel =0";
        return baseDao.queryHqlCount(hql,hs);
    }

    public int delOrder(List<Integer> list){
        StringBuffer hql = new StringBuffer("UPDATE Order set isDel = 1 WHERE ");
        if(list.size() == 0)
            return 0;
        hql.append("id = " + list.get(0) + " ");
        for(int i=1;i<list.size();i++){
            hql.append("or id = " + list.get(i) + " ");
        }
        return baseDao.executeUpdateHql(hql.toString());
    }
}
