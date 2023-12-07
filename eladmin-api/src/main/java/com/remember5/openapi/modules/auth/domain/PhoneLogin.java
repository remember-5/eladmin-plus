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
package com.remember5.openapi.modules.auth.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 手机验证码登录
 *
 * @author wangjiahao
 * @date 2023/12/6 11:12
 */
@Data
public class PhoneLogin {



    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 短信验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String smsCode;



}
