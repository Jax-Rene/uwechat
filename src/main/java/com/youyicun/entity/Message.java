package com.youyicun.entity;


import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by johnny on 16/4/10.
 */
@Entity
@Table(name = "message")
@Proxy(lazy = true)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

 }

