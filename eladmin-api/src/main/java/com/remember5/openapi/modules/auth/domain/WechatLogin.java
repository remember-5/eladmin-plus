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

import javax.validation.constraints.NotNull;

/**
 * @author wangjiahao
 * @date 2023/12/6 11:18
 */
@Data
public class WechatLogin {

    @NotNull(message = "code不能为空")
    private String code;

    @NotNull(message = "encryptedData不能为空")
    private String encryptedData;

    @NotNull(message = "iv不能为空")
    private String iv;

}
