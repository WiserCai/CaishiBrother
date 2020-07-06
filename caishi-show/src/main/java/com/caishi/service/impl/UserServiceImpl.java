package com.caishi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.caishi.mapper.UserMaper;
import com.caishi.model.dto.LoginDto;
import com.caishi.model.entity.TUser;
import com.caishi.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public TUser queryUserByUsername(LoginDto loginDto){

        if (loginDto.getUsername()!= null && loginDto.getPassword()!=null){

            QueryWrapper queryWrapper =new QueryWrapper();

           queryWrapper.eq("username", loginDto.getUsername());

            TUser tUser = userMaper.selectOne(queryWrapper);
            if (tUser.getPassword().equals(loginDto.getPassword())){
             return tUser;
            }
        }
        return null;
    }
}
