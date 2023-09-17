package com.xxxx.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 * redis配置类,  主要实现序列化
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-17 15:56
 **/

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();//redisTemplate 对象
        //Key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //Value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());//序列化后也是json数据
        //Hash序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());//hash的key
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());//hash的value
        //注入连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
