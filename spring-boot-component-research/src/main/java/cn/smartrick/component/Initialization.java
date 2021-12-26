package cn.smartrick.component;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: 当应用启动成功后的初始化工作
 */

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initialization implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("应用启动成功...");
    }
}
