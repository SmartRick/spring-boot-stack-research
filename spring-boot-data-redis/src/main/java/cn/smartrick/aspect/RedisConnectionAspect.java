package cn.smartrick.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.smartrick.annotation.RedisCacheConfig;
import cn.smartrick.annotation.RedisCacheEvict;
import cn.smartrick.annotation.RedisCachePut;
import cn.smartrick.annotation.RedisCacheable;
import cn.smartrick.config.RedisConfig;
import cn.smartrick.utils.MysqlKeyValueUtil;
import cn.smartrick.utils.RedisConnectionCheckTimer;
import cn.smartrick.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * @Date: 2022/6/17 14:22
 * @Author: SmartRick
 * @Description: TODO
 */
@Slf4j
@Aspect
@Component
public class RedisConnectionAspect {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MysqlKeyValueUtil mysqlKeyValueUtil;
    @Autowired
    private RedisConfig redisConfig;
    private static final String CACHE_SEPARATOR = "::";

    @Pointcut("execution(public * cn.smartrick.utils.RedisUtil.*(..)) && !execution(public * cn.smartrick.utils.RedisUtil.*Alive(..))")
    public void redisUtilMethod() {
    }


    @Around("redisUtilMethod()")
    public Object redisUtilProxy(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("??????redisUtil??????");
        if (redisUtil.isAlive()) {
            try {
                return joinPoint.proceed(joinPoint.getArgs());
            } catch (SocketTimeoutException | JedisConnectionException | RedisConnectionFailureException e) {
                log.debug("redis???????????????" + e.getMessage());
            }
        } else {
            log.debug("?????????????????????");
            Class<? extends MysqlKeyValueUtil> aClass = mysqlKeyValueUtil.getClass();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Method declaredMethod = aClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (declaredMethod != null) {
                return declaredMethod.invoke(mysqlKeyValueUtil, joinPoint.getArgs());
            } else {
                log.error("??????????????????????????????{}?????????", method.getName());
            }
        }
        return defaultNullResult(joinPoint);
    }


    @Pointcut("@annotation(cn.smartrick.annotation.RedisCacheable) || @annotation(cn.smartrick.annotation.RedisCacheEvict) || @annotation(cn.smartrick.annotation.RedisCachePut)")
    public void serviceCacheMethod() {
    }

    @Around("serviceCacheMethod()")
    public Object cacheProxy(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("??????redisCache??????");
        if (redisUtil.isAlive()) {
            try {
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                Method method = signature.getMethod();
                RedisCacheable redisCacheable = method.getAnnotation(RedisCacheable.class);
                RedisCachePut redisCachePut = method.getAnnotation(RedisCachePut.class);
                RedisCacheEvict redisCacheEvict = method.getAnnotation(RedisCacheEvict.class);

                RedisCacheConfig redisCacheConfig = method.getDeclaringClass().getAnnotation(RedisCacheConfig.class);

                //??????Key????????????Cacheable????????????????????????
                String keyResolve = null;
                if (redisCacheable != null) {
                    keyResolve = keyResolve(redisCacheConfig, redisCacheable.cacheName(), redisCacheable.key(), method, joinPoint);
                    Object cache = getCache(keyResolve, method);
                    if (cache != null) return cache;
                } else if (redisCachePut != null) {
                    keyResolve = keyResolve(redisCacheConfig, redisCachePut.cacheName(), redisCachePut.key(), method, joinPoint);
                } else if (redisCacheEvict != null) {
                    //????????????Key
                    if (redisCacheEvict.allEntries()) {
                        if ((redisCacheConfig == null || StrUtil.isBlank(redisCacheConfig.cacheName()) && StrUtil.isBlank(redisCacheEvict.cacheName()))) {
                            throw new IllegalArgumentException("allEntries=true???????????????CacheName");
                        }
                        String cacheName = cacheNameResolve(redisCacheConfig, redisCacheEvict.cacheName());
                        removeCacheByCacheName(cacheName);
                    }
                    String[] keys = redisCacheEvict.keys();
                    if (keys.length == 1) {
                        keyResolve = keyResolve(redisCacheConfig, redisCacheEvict.cacheName(), redisCacheEvict.keys()[0], method, joinPoint);
                        removeCache(keyResolve);
                    } else if (keys.length > 1) {
                        //??????????????????Key
                        for (String key : keys) {
                            keyResolve = keyResolve(redisCacheConfig, redisCacheEvict.cacheName(), key, method, joinPoint);
                            removeCache(keyResolve);
                        }
                    }
                }

                //??????????????????
                Object proceed = joinPoint.proceed(joinPoint.getArgs());
                if ((redisCacheable != null || redisCachePut != null) && proceed != null) {
                    updateCache(keyResolve, proceed);
                }
                return proceed;
            } catch (JedisConnectionException | RedisConnectionFailureException e) {
                log.debug("redis???????????????" + e.getMessage());
            }
        } else {
            log.info("????????????????????????????????????");
            return joinPoint.proceed(joinPoint.getArgs());
        }
        return defaultNullResult(joinPoint);
    }

    /**
     * ????????????
     *
     * @param key
     * @param value
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void updateCache(String key, Object value) {
        redisUtil.set(key, value, redisConfig.getExpire().getSeconds());
    }

    /**
     * ????????????
     *
     * @param key
     */
    private void removeCache(String key) {
        Object cache = redisUtil.get(key);
        if (cache != null) {
            redisUtil.del(key);
        }
    }

    /**
     * ????????????
     *
     * @param cacheName
     */
    private void removeCacheByCacheName(String cacheName) {
        Set<Object> keys = redisUtil.keys(cacheName + "*");
        if (CollectionUtil.isNotEmpty(keys)) {
            redisUtil.del(keys);
        }
    }

    /**
     * ????????????
     *
     * @param key
     * @param method
     */
    private Object getCache(String key, Method method) throws IllegalAccessException, InstantiationException {
        Object cache = redisUtil.get(key);
        if (cache != null) {
            if (cache instanceof LinkedHashMap) {
                //??????Value
                LinkedHashMap linkedHashMap = (LinkedHashMap) cache;
                Object resultBean = method.getReturnType().newInstance();
                BeanUtil.fillBeanWithMap(linkedHashMap, resultBean, true);
                return resultBean;
            } else {
                //??????Value
                return cache;
            }
        }
        return null;
    }


    /**
     * key?????????
     *
     * @param key
     * @param method
     * @param joinPoint
     * @return
     */
    private String keyResolve(RedisCacheConfig redisCacheConfig, String subCacheName, String key, Method method, ProceedingJoinPoint joinPoint) throws NoSuchFieldException, IllegalAccessException {
        String cacheName = cacheNameResolve(redisCacheConfig, subCacheName);

        if (key.startsWith("#")) {
            String substring = key.substring(1);
            if ("root.methodName".equals(substring)) {
                return cacheName + method.getDeclaringClass().getName() + "." + method.getName();
            } else if (substring.startsWith("param")) {
                Object[] args = joinPoint.getArgs();
                String paramName = key.substring(7);
                //???????????????????????????
                Parameter[] parameters = method.getParameters();
                if (paramName.contains(".")) {
                    String[] split = paramName.split("\\.");
                    //?????????????????????????????????????????????,?????????preObj???
                    Object preObj = null;
                    for (int j = 0; j < parameters.length; j++) {
                        if (parameters[j].getName().equals(split[0])) {
                            preObj = args[j];
                            break;
                        }
                    }
                    if (preObj != null) {
                        for (int i = 1; i < split.length; i++) {
                            if (preObj != null) {
                                Field declaredField = preObj.getClass().getDeclaredField(split[i]);
                                declaredField.setAccessible(true);
                                preObj = declaredField.get(preObj);
                            } else {
                                throw new IllegalArgumentException("??????????????????" + paramName);
                            }
                        }
                        return cacheName + preObj.toString();
                    } else {
                        throw new IllegalArgumentException("??????????????????" + paramName);
                    }
                } else {
                    for (int i = 0; i < parameters.length; i++) {
                        if (parameters[i].getName().equals(paramName)) return cacheName + args[i].toString();
                    }
                }
            }
        }
        return cacheName + key;
    }


    private String cacheNameResolve(RedisCacheConfig redisCacheConfig, String subCacheName) {
        String cacheName = "";
        if (redisCacheConfig != null) {
            cacheName = redisCacheConfig.cacheName() + CACHE_SEPARATOR;
        }
        if (StrUtil.isNotBlank(subCacheName)) {
            cacheName += subCacheName + CACHE_SEPARATOR;
        }
        return cacheName;
    }

    private Object defaultNullResult(ProceedingJoinPoint joinPoint) {
        String name = getMethodReturnType(joinPoint).getSimpleName();
        log.info("?????????????????????" + name);
        switch (name) {
            case "boolean":
                return false;
            case "char":
                return "".charAt(0);
            case "int":
            case "byte":
            case "short":
            case "long":
            case "float":
            case "double":
                return 0;
            default:
                return null;
        }
    }

    private static Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) {
        // ?????????
        Object[] args = joinPoint.getArgs();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = pnd.getParameterNames(method);
        Map<String, Object> paramMap = new HashMap<>(32);
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                paramMap.put(parameterNames[i], args[i]);
            }
        }
        return paramMap;
    }

    private static Class getMethodReturnType(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getReturnType();
    }
}
