package com.caishi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @Author CYC
 * @Version 1.0.0
 * @Description
 **/
@Controller
public class HomeController {

    @GetMapping("test")
    public String test(){
        return "index";
    }

}



















