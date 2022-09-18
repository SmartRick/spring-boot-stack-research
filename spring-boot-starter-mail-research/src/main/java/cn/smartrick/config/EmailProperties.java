package cn.smartrick.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Component
@Validated
@ConfigurationProperties(prefix = "email-config")
@EnableConfigurationProperties(EmailProperties.class)
public class EmailProperties {
    @Pattern(regexp = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+",message = "配置邮箱不正确")
    private String notifyMail;
    @Pattern(regexp = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+",message = "配置邮箱不正确")
    private String senderMail;
    private String authCode;

    public String getNotifyMail() {
        return notifyMail;
    }

    public void setNotifyMail(String notifyMail) {
        this.notifyMail = notifyMail;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
