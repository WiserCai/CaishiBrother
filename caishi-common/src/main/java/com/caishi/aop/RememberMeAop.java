package com.caishi.aop;


import com.caishi.model.entity.TUser;
import com.caishi.util.JWTUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * @program: nz1903_plartform
 * @description(描述) 切面类，必须被spring扫描到
 * @author: WellCai
 * @create: 2020-05-29 11:02
 **/
@Aspect
@Component
public class RememberMeAop {
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
    @Around("@annotation(IsRemenberMe)")
    public String isLogin(ProceedingJoinPoint joinPoint){

        //前置增强
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        HttpServletResponse response =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        //获取所有cookie
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length>0) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    String cookieToken = cookie.getValue();
                    //解析令牌
                    TUser tUser = JWTUtil.parseJwtToken(cookieToken);
                    if (tUser != null) {
                        //执行目标方法
                        String url = null;
                        try {
                            url = (String) joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                        //令牌解析正确就跳转主页
                        return url;

                    }

                }
            }
        }

        return "index";
    }

}
















