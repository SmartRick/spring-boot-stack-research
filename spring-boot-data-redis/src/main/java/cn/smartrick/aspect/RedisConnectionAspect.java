package cn.smartrick.aspect;

import cn.smartrick.utils.RedisConnectionCheckTimer;
import cn.smartrick.utils.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.net.SocketTimeoutException;

/**
 * @Date: 2022/6/17 14:22
 * @Author: SmartRick
 * @Description: TODO
 */
@Aspect
@Component
public class RedisConnectionAspect {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisConnectionCheckTimer redisConnectionCheckTimer;

    @Pointcut("execution(public * cn.smartrick.utils.RedisUtil.*(..)) && !execution(public * cn.smartrick.utils.RedisUtil.*Alive(..))")
    public void redisUtilMethod() {
    }


    @Around("redisUtilMethod()")
    public Object beforePing(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("切入redis操作");
        if (redisUtil.isAlive()) {
            try {
                return joinPoint.proceed(joinPoint.getArgs());
            } catch (SocketTimeoutException | JedisConnectionException | RedisConnectionFailureException e) {
                System.out.println("redis连接异常：" + e.getMessage());
            }
        } else {
            System.out.println("降级操作数据库");
        }
        return null;
    }
}
