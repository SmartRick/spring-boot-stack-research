package cn.smartrick.listener;

import cn.smartrick.config.EmailConfig;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {
    @Resource
    private EmailConfig emailConfig;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("应用启动成功");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd-HH时:mm分:ss秒");
        String format = String.format("启动时间：%s\n应用名称：邮箱测试程序\nJava版本：%s\n操作系统：%s\n启动用户：%s\nJava类路径：%s",
                simpleDateFormat.format(new Date()),
                System.getProperty("java.version"),
                System.getProperty("os.name"),
                System.getProperty("user.name"),
                System.getProperty("java.class.path")
        );
        emailConfig.sendMail("应用启动", format);
    }


}
