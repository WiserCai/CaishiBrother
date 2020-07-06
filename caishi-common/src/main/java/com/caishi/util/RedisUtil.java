package com.caishi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author CYC
 * @Version 1.0.0
 * @Description
 **/
@Component
public class RedisUtil {

    //默认保存半小时
    private static final Integer REDIS_EXPIRE_TIME = 1800;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //获取key的失效时间
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    //缓存中是否拥有该key
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    //删除redis中的key的值
    public boolean deleteKey(String... keys) {
        for (String key : keys) {
            return redisTemplate.delete(key);
        }
        return false;
    }

    //1.存储String类型的值
    public boolean setString(String key,Object value,Long time){
        try {
            if (time==null||time<=0){

                redisTemplate.opsForValue().set(key,value,REDIS_EXPIRE_TIME,TimeUnit.SECONDS);
                return true;
            }
            redisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //2.存储list类型的值
    public boolean setList(String key,Object value,Long time){
        try {
            redisTemplate.opsForList().rightPush(key,value);
            if (time>0){
                redisTemplate.expire(key,time,TimeUnit.SECONDS);
            }else {
                redisTemplate.expire(key,REDIS_EXPIRE_TIME,TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //3.存储hash类型的值
    public boolean setHash(String key,String vk,Object value,Long time){
        try {
            redisTemplate.opsForHash().put(key,vk,value);
            if (time>0){
                redisTemplate.expire(key,time,TimeUnit.SECONDS);
            }else {
                redisTemplate.expire(key,REDIS_EXPIRE_TIME,TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //4.存储set类型的值
    public boolean setSet(String key, Long time, Object... value) {
        try {
            redisTemplate.opsForSet().add(key, value);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }else {
                redisTemplate.expire(key,REDIS_EXPIRE_TIME,TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //5.存储ZSet类型的值
    public boolean setZSet(String key, Object value, Double score, Long time) {
        try {
            redisTemplate.opsForZSet().add(key, value, score);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }else {
                redisTemplate.expire(key,REDIS_EXPIRE_TIME,TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //取String值
    public Object getStringValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    //取所有List值
    public Object getListValue(String key) {
        Long size = redisTemplate.opsForList().size(key);
        if (size==null||size==0){
            return null;
        }
        return redisTemplate.opsForList().range(key,0,size);
    }

    //取Hash值
    public Object getHashValue(String key,String name) {
        return redisTemplate.opsForHash().get(key,name);
    }

    //取Set值
    public Object getSetValue(String key) {
        return redisTemplate.opsForSet().members(key);
    }
    //取ZSet值
    public Object getZSetValue(String key,int start,int end) {
        return redisTemplate.opsForZSet().rangeByScore(key,start,end);
    }
}
