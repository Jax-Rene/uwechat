package com.youyicun.bean;

import java.util.Date;

/**
 * Created by johnny on 16/4/28.
 */
public class Message {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public Message() {
    }

    public Message(String toUserName, String fromUserName) {
        ToUserName = toUserName;
        FromUserName = fromUserName;
        CreateTime = new Date().getTime();
    }
}
