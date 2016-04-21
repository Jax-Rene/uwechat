package com.youyicun.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by johnny on 16/4/21.
 */
@Entity
@Table(name = "order")
@Proxy(lazy = false)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private Integer sex;

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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSex() {
        return sex;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getPeople() {
        return people;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer isSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
