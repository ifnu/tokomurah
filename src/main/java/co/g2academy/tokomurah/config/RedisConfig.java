/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

/**
 *
 * @author ifnub
 */
@Configuration
public class RedisConfig {
    @Value("${spring.pubsub.host}") private String host;
    @Value("${spring.pubsub.port}") private Integer port;

    @Bean
    public JedisConnectionFactory jedisConfiConnectionFactory(){
        //TODO localhost 6637
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        return factory;
    }
    @Bean(name = "redisPubSubTemplate")
    public RedisTemplate<String, String> redisTemplate(){
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(
                jedisConfiConnectionFactory());
        template.setValueSerializer(
                new GenericToStringSerializer<>(String.class));
        return template;
    }
}
