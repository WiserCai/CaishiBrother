package com.caishi.controller;


import com.caishi.aop.IsLogin;
import com.caishi.aop.IsRemenberMe;
import com.caishi.aop.LoginAopParms;
import com.caishi.model.dto.LoginDto;
import com.caishi.model.entity.TUser;
import com.caishi.service.IUserService;
import com.caishi.util.AuthCodeUtil;
import com.caishi.util.JWTUtil;
import com.caishi.util.RedisUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
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

    @GetMapping("hashLogin")
    @IsRemenberMe(hashRemenber = true)
    public String hashLogin() {

        return "home";
    }



    //登陆
    @PostMapping("loginWithUsernamePassword")
    @IsLogin
    public String loginWithUsernamePassword(LoginDto loginDto, HttpServletResponse response) throws Exception {

        TUser tUser = userService.queryUserByUsername(loginDto,response);

        if (tUser != null){
            LoginAopParms.setLocalUser(tUser);
        }

        return "redirect:/login/hashLogin";

    }


}






















