package com.caishi.service;

import com.caishi.model.dto.LoginDto;
import com.caishi.model.entity.TUser;

/**
 * @Author CYC
 * @Date 2020/7/6 0006 21:38
 * @Version 1.0.0
 * @Description
 **/
public interface IUserService {
    TUser queryUserByUsername(LoginDto loginDto);
}
