package com.caishi.controller;


import com.caishi.model.dto.LoginDto;
import com.caishi.model.entity.TUser;
import com.caishi.service.IUserService;
import com.caishi.util.AuthCodeUtil;
import com.caishi.util.RedisUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @Author CYC
 * @Version 1.0.0
 * @Description
 **/
@Controller
@RefreshScope
@RequestMapping("login")
public class LoginController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private IUserService userService;

    @Resource
    private AuthCodeUtil authCodeUtil;

    //生成验证码
    @GetMapping("captchaImage")
    public void getAuthCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        authCodeUtil.getAuthCode(request, response);
    }

    @GetMapping("gotoLogin")
    public String gotoLgon() {

        return "index";
    }

    //登陆
    @PostMapping("loginWithUsernamePassword")
    @ResponseBody
    public String loginWithUsernamePassword(LoginDto loginDto,HttpServletRequest request) throws UnknownHostException {

        String authCode = loginDto.getAuthCode();

        //获取当前请求的IP
        Integer hearHash = AuthCodeUtil.getIpAddress(request);

        Object auth_code = redisUtil.getStringValue("auth_code"+hearHash);

        if (authCode.equals(auth_code)) {
            TUser tUser = userService.queryUserByUsername(loginDto);

            //验证码失效
            redisUtil.deleteKey("auth_code"+hearHash);
            if (tUser!=null){
                return "登陆成功";
            }else {
                return "账号或密码错误";
            }

        }
        //验证码失效
        redisUtil.deleteKey("auth_code"+hearHash);
        return "验证码错误";
    }


}






















