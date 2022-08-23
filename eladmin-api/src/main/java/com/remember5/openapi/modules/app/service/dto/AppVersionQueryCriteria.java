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
package com.remember5.openapi.modules.app.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

/**
 * @author tianhh
 * @website https://el-admin.vip
 * @date 2021-05-04
 **/
@Data
public class AppVersionQueryCriteria {

    /**
     * 精确
     */
    @Query
    private String versionName;

    /**
     * 精确
     */
    @Query
    private String buildCode;

    /**
     * 精确
     */
    @Query
    private Boolean isDeleted;

    /**
     * 精确
     */
    @Query
    private Boolean isNew;

    /**
     * 资源类型 1、app 2、wgt
     */
    @Query
    private Integer resType;
}
