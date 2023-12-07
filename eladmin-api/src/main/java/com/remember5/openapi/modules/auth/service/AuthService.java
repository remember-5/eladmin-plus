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
package com.remember5.openapi.modules.auth.service;

import com.remember5.openapi.modules.auth.domain.*;

/**
 * @author wangjiahao
 * @date 2023/12/7 13:28
 */
public interface AuthService {

    /**
     * 注册
     *
     * @param usernameRegister /
     */
    boolean register(UsernameRegister usernameRegister);

    /**
     * 获取图片验证码
     *
     * @return /
     */
    CaptchaDTO getCode();


    /**
     * 账号登录
     *
     * @param usernameLogin /
     * @return /
     */
    TokenDTO login(UsernameLogin usernameLogin);

    /**
     * 验证码登录
     *
     * @param phoneLogin /
     * @return /
     */
    TokenDTO loginBySMS(PhoneLogin phoneLogin);

    /**
     * 微信小程序登录
     *
     * @param wechatLogin /
     * @return /
     */
    TokenDTO loginByWechat(WechatLogin wechatLogin);

    boolean logout();
}
