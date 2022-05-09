package com.remember5.openapi.modules.apiuser.service.mapstruct;

import com.remember5.openapi.modules.apiuser.domain.ApiUser;
import com.remember5.openapi.modules.apiuser.service.dto.ApiUserDto;
import me.zhengjie.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author fly
 * @description
 * @date 2021/8/20 17:58
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiUserMapper extends BaseMapper<ApiUserDto, ApiUser> {
}
