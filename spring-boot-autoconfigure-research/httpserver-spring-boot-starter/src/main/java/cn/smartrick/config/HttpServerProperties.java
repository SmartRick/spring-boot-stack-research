package cn.smartrick.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Date: 2022/1/15
 * @Author: SmartRick
 * @Description: TODO
 */

@ConfigurationProperties(prefix = "http.server")
public class HttpServerProperties {
    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
