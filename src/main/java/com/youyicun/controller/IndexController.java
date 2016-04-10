package com.youyicun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by johnny on 16/4/8.
 */
@Controller
public class IndexController {
    @RequestMapping("/book")
    public String book(){
        return "index";
    }

    @RequestMapping("/message")
    public String message(){
        return "message";
    }



}
