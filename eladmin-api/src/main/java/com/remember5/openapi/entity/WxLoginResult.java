package com.remember5.openapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信小程序登录后的结果
 *
 * @author wangjiahao
 * @date 2022/12/12 14:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxLoginResult {


    /**
     * 当前对话的sessionKey
     */
    private String sessionKey;

    /**
     * 用户的openid
     */
    private String openid;

    /**
     * 开放平台的id
     */
    private String unionid;

    /**
     * 用户绑定的手机号（国外手机号会有区号）
     */
    private String phoneNumber;

    /**
     * 没有区号的手机号
     */
    private String purePhoneNumber;
}
