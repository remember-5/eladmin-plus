package com.remember5.push.config;

import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.GtApiConfiguration;
import com.remember5.push.properties.PushProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注入app推送
 *
 * @author wangjiahao
 * @date 2022/5/18 17:09
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class UniappPushConfiguration {

    private final PushProperties pushProperties;

    /**
     * 生成ApiHelper
     *
     * @return bean
     */
    @Bean
    @ConditionalOnProperty(prefix = "getui", value = "enabled", matchIfMissing = true)
    public ApiHelper initApiHelper() {
        System.setProperty("http.maxConnections", pushProperties.getMaxConnections());
        GtApiConfiguration apiConfiguration = new GtApiConfiguration();
        //填写应用配置
        apiConfiguration.setAppId(pushProperties.getAppid());
        apiConfiguration.setAppKey(pushProperties.getAppkey());
        apiConfiguration.setMasterSecret(pushProperties.getMasterSecret());
        // 接口调用前缀，请查看文档: 接口调用规范 -> 接口前缀, 可不填写appId
        apiConfiguration.setDomain(pushProperties.getDomain());
        // 实例化ApiHelper对象，用于创建接口对象
        return ApiHelper.build(apiConfiguration);
    }

}
