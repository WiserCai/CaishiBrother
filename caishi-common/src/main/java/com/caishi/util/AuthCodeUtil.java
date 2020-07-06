package com.caishi.util;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import sun.security.provider.MD5;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import java.util.Base64;
/**
 * @Author CYC
 * @Date 2020/7/6 0006 20:58
 * @Version 1.0.0
 * @Description
 **/
@Component
public class AuthCodeUtil {
    @Resource
    RedisUtil redisUtil;

    public void getAuthCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设定变量为图片尺寸赋值
        int width = 60;
        int height = 30;
        //设定图片尺寸和图片颜色类型RGB
        BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //通过图片对象获取画笔
        Graphics graphics = bImage.getGraphics();
        //把画笔颜色设置为橙色
        graphics.setColor(Color.ORANGE);
        //填充背景颜色
        graphics.fillRect(0, 0, width, height);
        //把画笔颜色设置为黑色
        graphics.setColor(Color.black);
        //设置字体
        graphics.setFont(new Font("黑体", Font.BOLD, 25));

        //第二步
        //存储验证码的可变字符序列
        Random ran = new Random();
        StringBuffer sb = new StringBuffer();
        int code = 0;
        //生成随机验证码
        for (int i = 0; i < 4; i++) {
            //生成随机数
            code = ran.nextInt(10);
            //把随机数添加到图片中
            graphics.drawString(code + "", 10 + 10 * i, 25);
            sb.append(code);
        }
        //获取当前请求头hashcode
        Integer hearHash = AuthCodeUtil.getIpAddress(request);

        //通过Redis上传验证码
        redisUtil.setString("auth_code"+hearHash, sb.toString(),120L);

        //改变画笔颜色
        graphics.setColor(Color.GREEN);
        //绘制干扰线
        for (int i = 0; i < 10; i++) {
            // 干扰线x轴的开始,y轴的开始,x轴的结束,y轴的结束
            graphics.drawLine(ran.nextInt(width), ran.nextInt(height), ran.nextInt(width), ran.nextInt(height));
        }
        //把绘制好的验证码图片通过IO流发送到请求方
        ImageIO.write(bImage, "jpg", response.getOutputStream());

    }

    /**
     * 获取请求的hashcode
     */
    public static Integer getIpAddress(HttpServletRequest request) throws UnknownHostException {
        String userAgent = request.getHeader("User-Agent");

        return userAgent.hashCode();
    }

}
