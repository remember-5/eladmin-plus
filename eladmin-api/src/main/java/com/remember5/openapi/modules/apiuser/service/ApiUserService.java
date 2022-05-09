package com.remember5.openapi.modules.apiuser.service;

import com.remember5.openapi.modules.apiuser.domain.ApiUser;
import com.remember5.openapi.modules.apiuser.service.dto.ApiUserDto;
import com.remember5.openapi.modules.apiuser.service.dto.LoginUser;
import me.zhengjie.result.R;

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
     * @param user /
     * @return /
     */
    R loginByAccount(LoginUser user);


    /**
     * 验证码登录
     * @param user /
     * @return /
     */
    R loginBySms(LoginUser user);

    boolean phoneExits(String phone);

}
