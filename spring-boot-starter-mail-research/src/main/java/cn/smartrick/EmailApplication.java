package cn.smartrick;

import cn.smartrick.config.EmailConfig;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class EmailApplication {
    @Resource
    private EmailConfig emailConfig;

    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }

    @PreDestroy
    public void destroySendMail() {
        System.out.println("应用即将关闭");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd-HH时:mm分:ss秒");
        String format = String.format("关闭时间：%s\n应用名称：邮箱测试程序\nJava版本：%s\n操作系统：%s\n启动用户：%s\nJava类路径：%s",
                simpleDateFormat.format(new Date()),
                System.getProperty("java.version"),
                System.getProperty("os.name"),
                System.getProperty("user.name"),
                System.getProperty("java.class.path")
        );
        emailConfig.sendMail("应用关闭", format);
    }
}
