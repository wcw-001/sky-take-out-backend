package com.sky.utils;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil{

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */

    public String upload(byte[] bytes, String objectName) {
        try {
            // 密钥配置
            Auth auth = Auth.create(accessKeyId, accessKeySecret);
            // 构造一个带指定Zone对象的配置类,不同的七云牛存储区域调用不同的zone
            Configuration cfg = new Configuration(Zone.zone2());
            // ...其他参数参考类注释
            UploadManager uploadManager = new UploadManager(cfg);
            // 简单上传，使用默认策略，只需要设置上传的空间名就可以了(auth.uploadToken(bucketName))
            // 调用put方法上传
            Response res = uploadManager.put(bytes, objectName, auth.uploadToken(bucketName));
            // 打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 返回这张存储照片的地址
                return endpoint + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                log.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (IOException e) {
            // 请求失败时打印的异常的信息
            log.error("七牛异常:" + e.getMessage());
            return null;
        }
    }
}

