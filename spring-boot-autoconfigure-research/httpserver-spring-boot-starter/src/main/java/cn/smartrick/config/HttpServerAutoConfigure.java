package cn.smartrick.config;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.net.httpserver.HttpServerImpl;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @Date: 2022/1/15
 * @Author: SmartRick
 * @Description: TODO
 */
@Configuration
@EnableConfigurationProperties(HttpServerProperties.class)
public class HttpServerAutoConfigure {
    private static Logger logger = LoggerFactory.getLogger(HttpServerAutoConfigure.class);
    @Resource
    private HttpServerProperties serverProperties;

    @Bean
    @ConditionalOnClass(HttpServer.class)
    public HttpServer httpPro() throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(serverProperties.getPort());
        HttpServer httpServer = HttpServer.create(inetSocketAddress, 0);
        httpServer.start();
        logger.info(">>>>>>>> 启动服务成功");
        return httpServer;
    }
}
