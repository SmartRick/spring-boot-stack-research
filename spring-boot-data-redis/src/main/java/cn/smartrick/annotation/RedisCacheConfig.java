package cn.smartrick.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Redis缓存注解，只缓存返回值不为空的情况
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheConfig {
    String cacheName() default "";
}
