package com.caishi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author CYC
 * @Date 2020/7/6 0006 20:08
 * @Version 1.0.0
 * @Description
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String username;
    private String password;
    private String authCode;
}

















