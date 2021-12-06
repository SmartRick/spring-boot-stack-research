package cn.smartrick;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Date: 2021年12月4日
 * @Author: SmartRick
 * @Description: TODO
 */
@SpringBootApplication
@MapperScan("cn.smartrick.mapper")
public class SpringBootSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityApplication.class);
    }
}
