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
package com.remember5.sms.properties;

import lombok.Data;

/**
 * @author wangjiahao
 * @date 2023/5/10 13:59
 */
@Data
public class DaHanProperties {

    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 短信签名,需要申请配置
     */
    private String sign;

}
