package com.bsu.znatoki.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void add(Object key, Object value, Integer timeout, TimeUnit timeUnit) {
        log.info(String.format("ADDING TO REDIS (%s, %s)", key, value));
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public Object retrieve(Object key) {
        if (!contains(key)) {
            return null;
        }
        log.info("RETRIEVING FROM REDIS BY KEY "+ key);
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean contains(Object key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
