package com.caishi.controller;

import com.caishi.util.RedisUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * @Author CYC
 * @Version 1.0.0
 * @Description
 **/
@Controller
@RefreshScope //支持nacos的动态刷新功能
public class HomeController {

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("test")
    @ResponseBody
    public String test(){
        boolean b = redisUtil.setString("test", "test11", null);
        if (b){
            return "redis test succeed";
        }
        return "redis error";
    }

}



















