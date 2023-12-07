package com.remember5.openapi.modules.apiuser.service;

import com.remember5.openapi.modules.apiuser.service.dto.ApiUserDto;

/**
 * @author fly
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



}
