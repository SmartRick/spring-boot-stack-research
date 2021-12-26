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
        String jndi = "jndi:ldap://127.0.0.1:xxxx/Log4j";
        jndi = "jndi:rmi://127.0.0.1:xxxx/Log4j";
        SpringApplication.run(SpringBootSecurityApplication.class);
    }
}
