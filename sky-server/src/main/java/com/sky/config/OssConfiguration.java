package com.sky.config;

import com.sky.properties.QiNiuProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(QiNiuProperties qiNiuProperties){
        log.info("开始创建七牛云文件工具类：{}",qiNiuProperties);
        return new AliOssUtil(qiNiuProperties.getEndpoint(),
                qiNiuProperties.getAccessKeyId(),
                qiNiuProperties.getAccessKeySecret(),
                qiNiuProperties.getBucketName());
    }
}
