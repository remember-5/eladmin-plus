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
package com.remember5.system.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件读取
 *
 * @author wangjiahao
 */
@Data
@Component
@ConfigurationProperties(prefix = "login")
public class LoginProperties {

    /**
     * 用户登录信息缓存
     */
    private boolean cacheEnable;
    /**
     * 账号单用户 登录
     */
    private boolean singleLogin;

    /**
     * 默认密码
     */
    private String defaultPassword;


}
