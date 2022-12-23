package com.bsu.znatoki.config.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class JedisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    RedisTemplate< Object, Object > redisTemplate() {
        final RedisTemplate< Object, Object > template = new RedisTemplate<>();
        template.setConnectionFactory( jedisConnectionFactory() );
        template.setKeySerializer( new GenericToStringSerializer<>(Object.class) );
        template.setHashValueSerializer(new GenericToStringSerializer<>(Object.class) );
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class) );
        return template;
    }
}
