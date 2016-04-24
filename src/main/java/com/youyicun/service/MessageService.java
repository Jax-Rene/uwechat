package com.youyicun.service;

import com.youyicun.dao.BaseDao;
import com.youyicun.entity.Message;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by johnny on 16/4/21.
 */
@Service
public class MessageService {
    @Autowired
    private BaseDao<Message> baseDao;

    public void submitMsg(Message message){
        baseDao.add(message);
    }

    public List<Message> load(Integer start,Integer limit){
        return baseDao.find(Message.class,null,start,limit);
    }

    public Long countMsgNum(){
        return baseDao.count(Message.class,null);
    }


    public BigDecimal avgScore(){
        return (BigDecimal) baseDao.uniqueSqlResult("select AVG(`score`) from `umessage`");
    }

}
