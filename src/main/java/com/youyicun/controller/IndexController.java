package com.youyicun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String message(String code,Model model){
        model.addAttribute("code",code);
        return "message";
    }


    @RequestMapping("/order")
    public String order(String code,Model model){
        model.addAttribute("code",code);
        return "order";
    }
}
