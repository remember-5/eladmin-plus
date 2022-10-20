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

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author fly
* @date 2023-04-06
**/
@Data
public class AppVersionDto implements Serializable {

    /** 主键索引 */
    private Integer id;

    /** app的唯一标识*/
    private String appId;

    /** app的名称 */
    private String appName;

    /** 版本号 as 1.0.0 */
    private String version;

    /** 构建版本 as 400 */
    private Long build;

    /** 版本标题 as 3.0版本隆重登场 */
    private String title;

    /** 版本更新内容,主要在app上展示 */
    private String info;

    /** 最低支持的版本 as 1.0.2 */
    private String minVersion;

    /** 更新方式 forcibly = 强制更新, solicit = 弹窗确认更新, silent = 静默更新 */
    private String updateType;

    /** 平台 ios/android/app(ios&android) */
    private String platform;

    /** wgt */
    private String wgtUrl;

    /** apk */
    private String apkUrl;

    /** 是否发布 0=未发布 1=发布 */
    private Integer published;

    /** 是否归档 0=未归档 1=归档 可在拦截器判断，归档的版本禁止提供服务 */
    private Integer archived;

    /** 逻辑删除 0=未删除 1=删除 */
    private Integer isDeleted;

    /** 最新版本 0=不是最新 1=最新 */
    private Integer isLatestRelease;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 创建时间 */
    private Timestamp createTime;
}
