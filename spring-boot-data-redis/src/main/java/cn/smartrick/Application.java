package cn.smartrick;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Date: 2022/6/17 13:42
 * @Author: SmartRick
 * @Description:
 */
@MapperScan(basePackages = "cn.smartrick.mapper")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
