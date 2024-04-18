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
package com.remember5.security.config;

import com.remember5.security.handler.JwtAccessDeniedHandler;
import com.remember5.security.handler.JwtAuthenticationEntryPoint;
import com.remember5.security.properties.JwtProperties;
import com.remember5.security.utils.TokenProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

/**
 * 安全自动化配置
 *
 * @author wangjiahao
 * @date 2023/5/6 13:58
 */

public class CustomSecurityAutoConfiguration {


    /**
     * 用于处理403类型异常
     */
    @Primary
    @Bean
    public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    /**
     * 用于处理401类型异常
     */
    @Primary
    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }


    @Bean(name = "tokenProvider")
    @ConditionalOnBean(JwtProperties.class)
    public TokenProvider initTokenProvider(JwtProperties jwtProperties) {
        return new TokenProvider(jwtProperties);
    }

    /**
     * 常见的场景：项目中使用了 Spring Security 安全框架导致 CORS 跨域配置失效
     * CORS 配置失效解决方案
     * 配置 CorsFilter 优先于 SpringSecurityFilter 执行；
     * <a href="https://www.cnblogs.com/haoxianrui/p/17338196.html">跨域配置</a>
     * 配置跨域
     * @return 跨域
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //1.允许任何来源
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        //2.允许任何请求头
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        //3.允许任何方法
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        //4.允许凭证
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        CorsFilter corsFilter = new CorsFilter(source);

        FilterRegistrationBean<CorsFilter> filterRegistrationBean=new FilterRegistrationBean<>(corsFilter);
        filterRegistrationBean.setOrder(-101);  // 小于 SpringSecurity Filter的 Order(-100) 即可

        return filterRegistrationBean;
    }

}
