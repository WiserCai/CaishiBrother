package com.caishi.util;

import io.jsonwebtoken.*;
import com.caishi.model.entity.TUser;


import java.util.Date;

/**
 * @program: nz1903_plartform
 * @description(描述) JWT工具包
 * @author: WellCai
 * @create: 2020-05-29 00:26
 **/
public class JWTUtil {
    //1.定义密钥
    private static final String SECRET = "@my=tonken.cecureat-User7568*/QI+-=";

    //2.定义密钥得过期时间1天
    private static final Long TIME = 1*24*60*60*1000L;

    /**
     * 生成用户令牌
     * @return
     */
    public static String createUserToken(TUser user){
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(user.getId() + "") //设置令牌ID
                .setSubject(user.getUsername()) //设置签发者
                .setIssuedAt(new Date()) //设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + TIME))
                .signWith(SignatureAlgorithm.HS256,SECRET) //设置签名加密
                .claim("id", user.getId()) //设置用户带的信息
                .claim("username", user.getUsername())
                .claim("nickname", user.getNickName());

        return jwtBuilder.compact();

    }

    /**
     * 解析用户令牌
     * @return
     */
    public static TUser parseJwtToken(String token){
        TUser user = null;
        try {

            Claims body =(Claims) Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

            Integer id = (Integer) body.get("id");
            String username = String.valueOf(body.get("username"));
            String nickname = String.valueOf(body.get("nickname"));

            user = new TUser().setId(id).setUsername(username).setNickName(nickname);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return user;
    }


}
