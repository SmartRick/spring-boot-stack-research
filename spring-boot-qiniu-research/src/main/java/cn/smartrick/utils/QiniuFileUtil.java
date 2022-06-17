package cn.smartrick.utils;

import cn.smartrick.common.utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @author SmartRick
 * @date 2022年1月3日18:54:34
 * 七牛云OSS SDK封装，上传文件工具类
 */
@Slf4j
@Component
public class QiniuFileUtil {

    @Resource
    private UploadProperties uploadProperties;

    //构造一个带指定Region对象的配置类
    private Configuration cfg = new Configuration(Region.region2());

    private UploadManager uploadManager = new UploadManager(cfg);

    /**
     * 上传文件
     *
     * @param inputStream
     * @param originalFilename
     * @return
     * @throws IOException
     */
    public String uploadFile(InputStream inputStream, @NotNull String originalFilename) throws IOException {
        String uploadToken = getAuth().uploadToken(uploadProperties.getBucket());
        if (StringUtils.isEmpty(originalFilename)) throw new IllegalArgumentException("originalFilename不能为空");
        // 文件后缀
        int lastIndex = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(lastIndex);
        if (com.qiniu.util.StringUtils.isNullOrEmpty(suffix)) suffix = ".jpg";
        String fileKey = UUID.randomUUID().toString() + suffix;

        Response response = uploadManager.put(inputStream, fileKey, uploadToken, getPutPolicy(), null);

        log.debug(response.bodyString());
        DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);

        log.info(putRet.key);
        log.info(putRet.hash);
        return fileKey;
    }

    /**
     * 定义七牛云上传的相关策略
     */
    public StringMap getPutPolicy() {
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody",
                "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
        return stringMap;
    }

    /**
     * 删除七牛云上的相关文件
     *
     * @param key
     * @return
     * @throws QiniuException
     */
    public boolean delete(String key) throws QiniuException {
        BucketManager bucketManager = new BucketManager(getAuth(), cfg);
        Response response = bucketManager.delete(uploadProperties.getBucket(), key);
        int retry = 0;
        //判断是否需要 重试 删除 且重试次数为3
        while (response.needRetry() && retry++ < 3) {
            log.debug("正在尝试删除");
            response = bucketManager.delete(uploadProperties.getBucket(), key);
        }
        return response.statusCode == 200;
    }

    /**
     * 获取私有空间文件下载地址
     *
     * @param fileKey 要下载的文件名
     * @return
     */
    public String getPriPath(String fileKey) throws Exception {
        String encodedFileName = URLEncoder.encode(fileKey, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", uploadProperties.getDomain(), encodedFileName);
//        //1小时，可以自定义链接过期时间
//        long expireInSeconds = 3600;
        return getAuth().privateDownloadUrl(publicUrl);
    }


    /**
     * 通过发送http get 请求获取文件资源
     *
     * @return
     */
    public byte[] loadFile(String fileKey) throws Exception {
        String downloadUrl = getPriPath(fileKey);
        log.info(downloadUrl);
        try {
            HttpUtil.HttpRespWarp httpRespWarp = HttpUtil.get(downloadUrl);

            if (httpRespWarp.isSuccess()) return httpRespWarp.toBodyByte();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Unexpected code " + e.getMessage());
        }
        return null;
    }

    /**
     * 获取认证对象
     *
     * @return
     */
    private Auth getAuth() {
        return Auth.create(uploadProperties.getAccessKey(), uploadProperties.getSecretKey());
    }

}