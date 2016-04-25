package com.youyicun.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by johnny on 16/4/21.
 */
@Entity
@Table(name = "uorder")
@Proxy(lazy = false)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "sex")
    private Integer sex;

    @Transient
    private String nickName;

    @Transient
    private String call;

    @Column(name = "phone")
    private String phone;

    @Column(name = "people")
    private Integer people;

    @Column(name = "order_time")
    private String orderTime;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "success")
    private Integer success = 0; //0:失败 1:成功

    @Column(name = "is_del")
    private Integer isDel;


    public String getCall() {
        if(getSex() == 0)
            return getLastName() + "先生";
        else
            return getLastName() + "小姐";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Order() {
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
