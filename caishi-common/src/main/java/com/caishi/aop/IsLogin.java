package com.caishi.aop;

import java.lang.annotation.*;

/**
 * @program: nz1903_plartform
 * @description(描述)
 * @author: WellCai
 * @create: 2020-05-29 09:58
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLogin {

    //设置注解的方法
//    String method() default "hello";
    //表示该方法是否需要强制登陆
    boolean mustLogin() default false;
}
