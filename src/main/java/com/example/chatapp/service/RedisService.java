package com.example.chatapp.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToRedis(String key, String value, long expirationTime) {
        redisTemplate.opsForValue().set(key, value, expirationTime, TimeUnit.SECONDS);
    }

    public String getFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteFromRedis(String key) {
        redisTemplate.delete(key);
    }
}



