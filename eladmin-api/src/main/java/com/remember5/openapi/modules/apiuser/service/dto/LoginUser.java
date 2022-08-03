package com.remember5.openapi.modules.apiuser.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author fly
 * @description
 * @date 2021/8/23 15:21
 */
@Data
public class LoginUser implements Serializable {

    /** 手机号 */
    @NotBlank
    private String phone;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 短信验证码 */
    private String smsCode;

    /** 登录方式类型 1.手机号密码 2.手机号验证码 3.一键登录认证方式等 */
    private Integer loginType;

    private String code;

    private String uuid = "";

}
