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
package com.remember5.captcha.config;

import com.remember5.captcha.properties.CaptchaCodeProperties;
import com.remember5.captcha.core.CaptchaCodeProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 验证码工具自动配置
 *
 * @author wangjiahao
 * @date 2023/12/13 15:20
 */
@AutoConfiguration
@EnableConfigurationProperties(CaptchaCodeProperties.class)
public class CaptchaAutoConfiguration {

    @Bean
    public CaptchaCodeProvider captchaCodeProvider(CaptchaCodeProperties captchaCode) {
        return new CaptchaCodeProvider(captchaCode);
    }
}
