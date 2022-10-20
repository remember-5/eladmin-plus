/**
* Copyright [2022] [remember5]
* <p>
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* <p>
* http://www.apache.org/licenses/LICENSE-2.0
* <p>
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.remember5.biz.appversion.service.dto;

import com.remember5.core.annotation.Query;
import lombok.Data;

/**
* @author fly
* @date 2023-04-06
**/
@Data
public class AppVersionQueryCriteria{

    /** app的唯一标识 */
    @Query
    private String appId;

    /** 版本号*/
    @Query
    private String version;

    /** 构建版本*/
    @Query
    private Long build;

    /**  最低支持的版本 */
    @Query
    private String minVersion;

    /** 是否发布 0=未发布 1=发布 */
    @Query
    private Integer published;

    /** 是否归档 0=未归档 1=归档 可在拦截器判断，归档的版本禁止提供服务 */
    @Query
    private Integer archived;

    /** 逻辑删除 0=未删除 1=删除 */
    @Query
    private Integer isDeleted = 0;

    /** 最新版本 0=不是最新 1=最新 */
    @Query
    private String isLatestRelease;
}
