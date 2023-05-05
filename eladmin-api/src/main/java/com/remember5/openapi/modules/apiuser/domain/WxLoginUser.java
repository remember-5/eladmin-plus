package com.remember5.openapi.modules.apiuser.domain;

import lombok.Data;

/**
 * 微信小程序登录用户信息
 * @author wangjiahao
 * @date 2022/9/24 09:52
 */
@Data
public class WxLoginUser {

    private String cloudID;
    private String code;
    private String encryptedData;
    private String errMsg;
    private String iv;
    private String wxCode;
    private String sessionKey;

}
