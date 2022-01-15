package cn.smartrick.utils;

/**
 * @author: SmartRick
 * @Date: 2019/12/16
 * 七牛云上传配置
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {
    /**
     * 域名
     */
    private String domain;

    /**
     * 从下面这个地址中获取accessKey和secretKey
     * https://portal.qiniu.com/user/key
     */
    private String accessKey;

    private String secretKey;

    /**
     * 存储空间名
     */
    private String bucket;
}