package com.caishi.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.tomcat.util.security.MD5Encoder;

/**
 * @Author CYC
 * @Version 1.0.0
 * @Description
 **/
public class MD5HashUtil {

    private static final String SALT = "dsdklasndjkcb312*/-DF";

    public static String encode(String code,String salt,Integer standby){

        String encode = new Md5Hash(code, salt + SALT, standby).toBase64();

        return encode;
    }

}
