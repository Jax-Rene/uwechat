package com.youyicun.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by johnny on 16/4/28.
 */
public class NewsMessage extends Message{
    private String MsgType = "news";
    private Integer ArticleCount;
    private List<Item> Articles;


    public NewsMessage(String toUserName, String fromUserName,Integer articleCount) {
        super(toUserName, fromUserName);
        this.ArticleCount = articleCount;
    }


    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public List<Item> getArticles() {
        return Articles;
    }

    public void setArticles(List<Item> articles) {
        Articles = articles;
    }
}
