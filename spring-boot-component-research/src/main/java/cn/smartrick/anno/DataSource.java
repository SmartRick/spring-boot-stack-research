package cn.smartrick.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: 通过该注解指定方法执行时使用数据源
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    DataSourceType value();
}
