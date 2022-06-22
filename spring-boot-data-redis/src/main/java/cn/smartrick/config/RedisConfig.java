package cn.smartrick.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @Date: 2022/6/17 14:01
 * @Author: SmartRick
 * @Description: TODO
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "redis-cache")
public class RedisConfig {
    /**
     * 默认缓存过期时间8s
     */
    private Duration expire = Duration.ofSeconds(8);


    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        //使用fastjson序列化
        Jackson2JsonRedisSerializer fastJsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        return template;
    }


}
