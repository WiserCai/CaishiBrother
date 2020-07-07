package com.caishi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.caishi.constant.StatusEnum;
import com.caishi.exception.LoginAuthException;
import com.caishi.mapper.UserMaper;
import com.caishi.model.dto.LoginDto;
import com.caishi.model.entity.TUser;
import com.caishi.service.IUserService;
import com.caishi.util.MD5HashUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * @Author CYC
 * @Date 2020/7/6 0006 21:47
 * @Version 1.0.0
 * @Description
 **/
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMaper userMaper;

    //登陆，根据用户名查询用户
    @Override
    public TUser queryUserByUsername(LoginDto loginDto, HttpServletResponse response) throws LoginAuthException{

        if (loginDto.getUsername()!= null && loginDto.getPassword()!=null){

                QueryWrapper queryWrapper =new QueryWrapper();
                queryWrapper.eq("username", loginDto.getUsername());
                TUser tUser = userMaper.selectOne(queryWrapper);
            //密码加密比较
            String pwd = MD5HashUtil.encode(loginDto.getPassword(), tUser.getSalt(), Integer.valueOf(tUser.getStandby()));

            if (tUser != null && pwd.equals(tUser.getPassword())){

             return tUser;
            }

        }


        Cookie authResult = new Cookie("authResult","登陆信息错误请重新输入");
        authResult.setMaxAge(5);
        response.addCookie(authResult);
        throw new LoginAuthException(StatusEnum.AUTH_ERROR);
    }

    //注册
    @Override
    public String userRegister(TUser tUser) throws RuntimeException{

        try {
            QueryWrapper queryWrapper =new QueryWrapper();
            queryWrapper.eq("username", tUser.getUsername());
            TUser tUser1 = userMaper.selectOne(queryWrapper);
            if (tUser1 == null){
                String salt = UUID.randomUUID().toString();
                tUser.setSalt(salt);

                String encode = MD5HashUtil.encode(tUser.getPassword(), salt, tUser.hashCode());
                tUser.setPassword(encode);
                tUser.setStandby(String.valueOf(tUser.hashCode()));
                tUser.setCreateTime(new Date());
                tUser.setUpdateTime(new Date());
            }

            int result = userMaper.insert(tUser);

            if (result>0){

                return "succeed";
            }
        } catch (Exception e) {
            return "failed";
        }
        return "failed";

    }


}
























