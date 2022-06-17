package cn.smartrick;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: TODO
 */
@MapperScan(basePackages = "cn.smartrick.mapper")
//@EnableTransactionManagement    //spring-boot-starter-jdbc自动配置事务管理器
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@EnableAspectJAutoProxy         //spring-boot-starter-aop自动配置AOP切面
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
