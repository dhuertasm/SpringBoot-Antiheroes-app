package com.dahuertasm.springbootsuperheroes.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    RedisTemplate<Long, Object> redisTemplate() {
        RedisTemplate<Long, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(jedisConnectionFactory());

        template.setKeySerializer(
                new StringRedisSerializer());

        template.setHashKeySerializer(
                new JdkSerializationRedisSerializer());

        template.setValueSerializer(
                new JdkSerializationRedisSerializer());

        template.setEnableTransactionSupport(true);

        template.afterPropertiesSet();

        return template;

    }

}
