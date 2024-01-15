/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.remember5.system.modules.system.mapper;

import com.remember5.system.modules.system.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2023-06-20
 */
@Mapper
public interface UserRoleMapper {
    void insertData(@Param("userId") Long userId, @Param("roles") Set<Role> roles);

    void deleteByUserId(@Param("userId") Long userId);

    void deleteByUserIds(@Param("userIds") Set<Long> userIds);
}
