package cn.smartrick.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Date: 2022/1/5 17:45
 * @Author: SmartRick
 * @Description: TODO
 */
@ConfigurationProperties(prefix = "cus")
@EnableConfigurationProperties(CustomProperties.class)
public class CustomProperties {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
