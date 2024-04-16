package com.example.parking.container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class RedisDataCleaner {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void delete() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }
}
