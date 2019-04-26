package com.chatting.util;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class Cache {
    private Logger logger = Logger.getLogger(Cache.class);
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    public boolean set(String key,Object value,int expire){
        try {
            redisTemplate.opsForValue().set(key,value,expire, TimeUnit.SECONDS);
            return true;
        }
        catch (Exception e){
            logger.error(e);
        }
        return false;
    }
    public boolean set(String key,Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }
    public Object get(String key){
        Object object = null;
        object = redisTemplate.opsForValue().get(key);
        return object;
    }
    public boolean remove(String key){
        try {
            if(isExists(key)) redisTemplate.delete(key);
            else return false;
            return true;
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }
    public boolean isExists(String key){
        return redisTemplate.hasKey(key);
    }
    public boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

}
