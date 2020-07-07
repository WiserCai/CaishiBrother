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
public @interface IsRemenberMe {

    //设置注解的方法
//    String method() default "hello";
    //是否需要校验免登陆
    boolean hashRemenber() default false;
}
