/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import me.zhengjie.exception.BadRequestException;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import java.util.concurrent.BlockingDeque;

/**
 * 验证工具
 *
 * @author Zheng Jie
 * @date 2018-11-23
 */
public class ValidationUtil {

    /**
     * see
     */
    public static String PATTERN_PHONE = "^(?:\\+?86)?1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[0-35-9]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[0-35-9]\\d{2}|6[2567]\\d{2}|4(?:(?:10|4[01])\\d{3}|[68]\\d{4}|[579]\\d{2}))\\d{6}$";
    /**
     * 验证空
     */
    public static void isNull(Object obj, String entity, String parameter, Object value) {
        if (ObjectUtil.isNull(obj)) {
            String msg = entity + " 不存在: " + parameter + " is " + value;
            throw new BadRequestException(msg);
        }
    }

    /**
     * 验证是否为邮箱
     */
    public static boolean isEmail(String email) {
        return new EmailValidator().isValid(email, null);
    }

    public static boolean isPhone(String phone){
        return ReUtil.isMatch(PATTERN_PHONE, phone);
    }

}
