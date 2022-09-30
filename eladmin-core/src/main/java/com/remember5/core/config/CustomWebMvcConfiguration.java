/**
 * Copyright [2022] [remember5]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.remember5.core.config;

import com.remember5.core.handler.ApiRequestMappingHandlerMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * WebMvcConfigurationSupport 和 WebMvcConfigurer
 *
 * @author wangjiahao
 * @date 2022/9/29 15:47
 */
@Slf4j
@Configuration
public class CustomWebMvcConfiguration extends WebMvcConfigurationSupport {

    /**
     * 注册ApiRequestMappingHandlerMapping
     * @return /
     */
    @Override
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new ApiRequestMappingHandlerMapping();
    }

    /**
     * 配置跨域
     * @param registry /
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        log.info("跨域已设置");
        // 设置允许跨域的路由
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);
    }
}
