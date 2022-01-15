package cn.smartrick;

import cn.smartrick.utils.UploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Date: 2022/1/3
 * @Author: SmartRick
 * @Description: 七牛云对象存储服务SDK调研
 */
@SpringBootApplication
@EnableConfigurationProperties(UploadProperties.class)
public class QiniuSdkApplication {
    public static void main(String[] args) {
        SpringApplication.run(QiniuSdkApplication.class, args);
    }
}
