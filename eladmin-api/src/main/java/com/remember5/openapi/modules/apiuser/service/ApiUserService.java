package com.remember5.openapi.modules.apiuser.service;

import com.remember5.openapi.modules.apiuser.domain.ApiUser;
import com.remember5.openapi.modules.apiuser.domain.WxLoginUser;
import com.remember5.openapi.modules.apiuser.service.dto.ApiUserDto;
import com.remember5.openapi.modules.apiuser.service.dto.LoginUser;
import com.remember5.core.result.R;

/**
 * @author fly
 * @description
 * @date 2021/8/20 17:59
 */
public interface ApiUserService {

    /**
     * 根据手机号查用户信息
     *
     * @param phone 手机号
     * @return /
     */
    ApiUserDto findByPhone(String phone);

    /**
     * 创建用户
     *
     * @param resources /
     * @return /
     */
    ApiUserDto create(ApiUser resources);

    /**
     * 修改用户信息
     *
     * @param resources /
     */
    void update(ApiUser resources);

    /**
     * 注册
     *
     * @param user /
     */
    R register(LoginUser user);

    /**
     * 获取图片验证码
     *
     * @return /
     */
    R getCode();

    /**
     * 注销
     *
     * @param userId 用户id
     * @return /
     */
    R deleted(Long userId);

    /**
     * 账号登录
     *
     * @param user /
     * @return /
     */
    R loginByAccount(LoginUser user);

    /**
     * 验证码登录
     *
     * @param user /
     * @return /
     */
    R loginBySms(LoginUser user);

    /**
     * 忘记密码-发送短信验证码
     *
     * @param phone 用户传入的手机号
     * @return /
     */
    R captchaByResetPassword(String phone);

    /**
     * 忘记密码
     *
     * @param user 用户信息
     * @return /
     */
    R forgetPassword(LoginUser user);

    /**
     * 微信小程序登录
     *
     * @param wxLoginInfo /
     * @return /
     */
    R wxMiniAppLogin(WxLoginUser wxLoginInfo);


}
