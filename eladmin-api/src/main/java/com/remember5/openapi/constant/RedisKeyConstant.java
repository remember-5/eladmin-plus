package com.remember5.openapi.constant;

/**
 * @author fly
 * @date 2021/8/23 18:01
 */
public class RedisKeyConstant {
    /**
     * access token
     */
    public static final String USER_ACCESS_TOKEN = "JWT:TOKEN:ACCESS:";
    /**
     * refresh token
     */
    public static final String USER_REFRESH_TOKEN = "JWT:TOKEN:REFRESH:";

    /**
     * 验证码的key
     */
    public static final String CAPTCHA_KEY = "CAPTCHA:";
    /**
     * 验证码失效期
     */
    public static final Long CAPTCHA_KEY_INVALID = 60L * 5;
    /**
     * 验证码频率key
     */
    public static final String CAPTCHA_RATE_KEY = "CAPTCHA:RATE:";
    /**
     * 验证码频率失效期
     */
    public static final Long CAPTCHA_KEY_RATE_INVALID = 60L;

    /**
     * 重置密码发送的验证码
     */
    public static final String RESET_PWD_CAPTCHA_KEY = "RESET:PWD:CAPTCHA:";
    /**
     * 重置密码发送的验证码有效期
     */
    public static final Long RESET_PWD_CAPTCHA_INVALID = 60L * 5;
    /**
     * 重置密码发送的验证码附带的UUID，重置密码要带UUID过来
     */
    public static final String RESET_PWD_UID = "RESET:PWD:UID:";
}
