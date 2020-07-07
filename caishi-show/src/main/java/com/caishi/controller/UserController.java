package com.caishi.controller;

import com.caishi.model.entity.TUser;
import com.caishi.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;

/**
 * @Author CYC
 * @Version 1.0.0
 * @Description
 **/
@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping("gotoRegisterPage")
    public String gotoRegisterPage(){
        return "register";
    }

    @PostMapping("register")
    public String userRegister(TUser tUser){

        String register = userService.userRegister(tUser);

        if (register.equals("succeed")){

            return "home";

        }

        return "register";
    }
}
























