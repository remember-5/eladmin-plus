/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.remember5.core.properties;

import com.remember5.core.enums.CaptchaTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 登录验证码配置信息
 *
 * @author liaojinlong
 * @date 2020/6/10 18:53
 */
@Data
@Component
@ConfigurationProperties(prefix = "captcha")
public class CaptchaCodeProperties {

    /**
     * 验证码类型配置
     */
    private CaptchaTypeEnum type;
    /**
     * 验证码有效期 分钟
     * default value = 2L
     */
    private Long expiration;
    /**
     * 验证码内容长度
     * default value = 2
     */
    private int length;
    /**
     * 验证码宽度
     * default value = 111
     */
    private int width;
    /**
     * 验证码高度
     * default value = 36
     */
    private int height;
    /**
     * 验证码字体
     */
    private String fontName;
    /**
     * 字体大小
     * default value = 25
     */
    private int fontSize;

}
