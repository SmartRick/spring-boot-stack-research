package cn.smartrick.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2022/6/17 14:43
 * @Author: SmartRick
 * @Description: TODO
 */
@Component
@Slf4j
public class RedisConnectionCheckTimer implements Runnable {
    @Autowired
    public RedisUtil redisUtil;

    @Autowired
    public RedisTemplate<Object, Object> redisTemplate;

    private ScheduledExecutorService scheduledExecutorService;

    public RedisConnectionCheckTimer() {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);

    }

    public void start() {
        this.scheduledExecutorService.scheduleAtFixedRate(this, 0, 3, TimeUnit.SECONDS);
    }

    public void stop() {
        this.scheduledExecutorService.shutdown();
    }


    @Override
    public void run() {
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        try {
            RedisConnection connection = connectionFactory.getConnection();
            connection.close();
            redisUtil.setAlive(true);
        } catch (RedisConnectionFailureException e) {
            log.error("尝试重连redis失败：{}", e.getMessage());
            redisUtil.setAlive(false);
        }
    }
}
