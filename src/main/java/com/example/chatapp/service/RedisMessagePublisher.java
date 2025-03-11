package com.example.chatapp.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher {

    private final StringRedisTemplate redisTemplate;

    public RedisMessagePublisher(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(String topic, String message) {
        redisTemplate.convertAndSend(topic, message);
    }
}
