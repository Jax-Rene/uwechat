package com.youyicun.service;

import com.youyicun.dao.BaseDao;
import com.youyicun.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
