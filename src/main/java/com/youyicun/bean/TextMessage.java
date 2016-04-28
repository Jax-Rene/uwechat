package com.youyicun.bean;

/**
 * Created by johnny on 16/4/4.
 */
public class TextMessage extends Message{
    public TextMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName);
    }

    private String MsgType = "text";
    private String Content;
    private String MsgId;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
