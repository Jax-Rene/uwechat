package com.youyicun.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by johnny on 16/4/23.
 */
public class Admin {
    private String userName;
    private String passWord;

    public Admin(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public Admin() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
