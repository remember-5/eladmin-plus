package com.remember5.openapi.constant;

/**
 * redis key
 *
 * @author fly
 * @date 2021/8/23 18:01
 */
public interface RedisKeyConstant {
    /**
     * access token
     */
    String USER_ACCESS_TOKEN = "JWT:TOKEN:ACCESS:";
    /**
     * refresh token
     */
    String USER_REFRESH_TOKEN = "JWT:TOKEN:REFRESH:";

    /**
     * 验证码的key
     */
    String CAPTCHA_KEY = "CAPTCHA:";
    /**
     * 验证码失效期
     */
    Long CAPTCHA_KEY_INVALID = 60L * 5;
    /**
     * 验证码频率key
     */
    String CAPTCHA_RATE_KEY = "CAPTCHA:RATE:";
    /**
     * 验证码频率失效期
     */
    Long CAPTCHA_KEY_RATE_INVALID = 60L;

    /**
     * 重置密码发送的验证码
     */
    String RESET_PWD_CAPTCHA_KEY = "RESET:PWD:CAPTCHA:";
    /**
     * 重置密码发送的验证码有效期
     */
    Long RESET_PWD_CAPTCHA_INVALID = 60L * 5;
    /**
     * 重置密码发送的验证码附带的UUID，重置密码要带UUID过来
     */
    String RESET_PWD_UID = "RESET:PWD:UID:";
}
