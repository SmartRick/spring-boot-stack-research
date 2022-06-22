package cn.smartrick.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Redis缓存注解，只缓存返回值不为空的情况
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheable {
    /**
     * 缓存Key
     * 支持方法名称：#root.methodName
     * 支持参数值: #param.参数名.属性名...
     *
     * @return
     */
    String key() default "";

    /**
     * 缓存Key
     *
     * @return
     */
    String value() default "";

    String cacheName() default "";
}
