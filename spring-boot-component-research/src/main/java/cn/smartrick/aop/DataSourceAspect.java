package cn.smartrick.aop;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: TODO
 */

import cn.smartrick.anno.DataSource;
import cn.smartrick.anno.DataSourceType;
import cn.smartrick.config.DataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(1)   //尽量让数据源切换优先执行，因为在声明式事务开启后不能切换数据源
@Component
public class DataSourceAspect {

    @Pointcut("@annotation(cn.smartrick.anno.DataSource)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DataSource annotation = signature.getMethod().getAnnotation(DataSource.class);
        if (annotation == null) {
            DataSourceHolder.setDataSource(DataSourceType.FIRST.name());
            log.info("设置数据源为" + DataSourceType.FIRST.name());
        } else {
            DataSourceHolder.setDataSource(annotation.value().name());
            log.info("设置数据源为" + annotation.value());
        }
    }
}
