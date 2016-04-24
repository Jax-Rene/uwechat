package com.youyicun.entity;


import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by johnny on 16/4/10.
 */
@Entity
@Table(name = "umessage")
@Proxy
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "content")
    private String content;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "time")
    private String time;

    public Message() {
    }

    public Message(Integer score, String content) {
        this.score = score;
        this.content = content;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        if (score == null)
            this.score = 0;
        else
            this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

