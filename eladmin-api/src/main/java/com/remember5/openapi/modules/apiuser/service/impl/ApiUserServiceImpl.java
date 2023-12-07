package com.remember5.openapi.modules.apiuser.service.impl;

import com.remember5.openapi.modules.apiuser.repository.ApiUserRepository;
import com.remember5.openapi.modules.apiuser.service.ApiUserService;
import com.remember5.openapi.modules.apiuser.service.dto.ApiUserDto;
import com.remember5.openapi.modules.apiuser.service.mapstruct.ApiUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author fly
 * @date 2021/8/20 18:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiUserServiceImpl implements ApiUserService {

    private final ApiUserRepository apiUserRepository;
    private final ApiUserMapper apiUserMapper;

    @Override
    public ApiUserDto findByPhone(String phone) {
        return apiUserMapper.toDto(apiUserRepository.findByPhone(phone));
    }



}
