package cn.smartrick.config;

import cn.smartrick.utils.RedisConnectionCheckTimer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Date: 2022/6/17 14:54
 * @Author: SmartRick
 * @Description: TODO
 */
@Component
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    private RedisConnectionCheckTimer redisConnectionCheckTimer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("正在启动redis连接检测器");
        redisConnectionCheckTimer.start();
    }
}
