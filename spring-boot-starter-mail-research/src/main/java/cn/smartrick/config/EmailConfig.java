package cn.smartrick.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setPort(587);

        javaMailSender.setUsername(emailProperties.getSenderMail());
        javaMailSender.setPassword(emailProperties.getAuthCode());

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return javaMailSender;
    }


    public SimpleMailMessage makeMsg(String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailProperties.getSenderMail());
        simpleMailMessage.setTo(emailProperties.getNotifyMail());
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        return simpleMailMessage;
    }

    public SimpleMailMessage makeMsg(String targetMail, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailProperties.getSenderMail());
        simpleMailMessage.setTo(targetMail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        return simpleMailMessage;
    }

    public void sendMail(String subject, String text) {
        SimpleMailMessage simpleMailMessage = makeMsg(subject, text);
        javaMailSender().send(simpleMailMessage);
    }

    public void sendMail(String targetMail, String subject, String text) {
        SimpleMailMessage simpleMailMessage = makeMsg(targetMail, subject, text);
        javaMailSender().send(simpleMailMessage);
    }

}
