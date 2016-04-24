package com.youyicun.controller;

import com.youyicun.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by johnny on 16/4/12.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private Admin admin;

    @RequestMapping(value = "",method = RequestMethod.POST)
    public String admin(Model model, String userName, String passWord,HttpSession session){
        if(admin.getUserName().equals(userName) && admin.getPassWord().equals(passWord)){
            session.setAttribute("admin",true);
            return "admin";
        }
        else{
            model.addAttribute("loginError","密码错误,请重新输入!");
            return "login";
        }
    }
}
