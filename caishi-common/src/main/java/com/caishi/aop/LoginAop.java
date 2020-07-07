package com.caishi.aop;


import com.caishi.model.entity.TUser;
import com.caishi.util.AuthCodeUtil;
import com.caishi.util.JWTUtil;
import com.caishi.util.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.net.UnknownHostException;


/**
 * @program: nz1903_plartform
 * @description(描述) 切面类，必须被spring扫描到
 * @author: WellCai
 * @create: 2020-05-29 11:02
 **/
@Aspect
@Component
public class LoginAop {

    @Resource
    private RedisUtil redisUtil;
    /**
     * 实现增强
     * 1.前置增强
     * 2.后置增强
     * 3.环绕增强
     * 4.后置完成增强
     * 5.异常增强
     *
     * 实现一个环绕z增强     */
//    @Around("@annotation(IsLogin) && execution(* com.qf.controller.*.*(..))")
    @Around("@annotation(IsLogin)")
    public String isLogin(ProceedingJoinPoint joinPoint) {

        Integer hearHash = null;
        HttpServletResponse response = null;
        HttpServletRequest request = null;
        try {
        //前置增强
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        //获取当前请求的IP
        hearHash = AuthCodeUtil.getIpAddress(request);

        Object auth_code = redisUtil.getStringValue("auth_code" + hearHash);

        String authCode = request.getParameter("authCode");

        String avoidLogin = request.getParameter("avoidLogin");//免登陆



        if (auth_code.equals(authCode)){

                //执行目标方法
               joinPoint.proceed();

                //后置增强
                //生产JWT令牌并返回
                    TUser user = LoginAopParms.getLocalUser();
                    String userToken = JWTUtil.createUserToken(user);
                    Cookie cookie = new Cookie("jwtToken",userToken); //令牌
                    Cookie nickName = new Cookie("nickName",user.getNickName()); //昵称

                    //如果勾选了免登陆,7天免登陆
                    if ("remember-me".equals(avoidLogin)){
                        cookie.setMaxAge(60*60*24*7);
                        nickName.setMaxAge(60*60*24*7);
                    }
                    response.addCookie(cookie);
                    response.addCookie(nickName);

            return "home";

        }else {

            return "redirect:/login/hashLogin";
        }

        } catch (Throwable throwable) {

            //异常增强，账号或者密码错误
            return "redirect:/login/hashLogin";

        }finally {

            redisUtil.deleteKey("auth_code" + hearHash);

        }

    }

}
















