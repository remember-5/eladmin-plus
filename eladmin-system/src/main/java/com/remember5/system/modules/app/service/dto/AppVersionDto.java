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
package com.remember5.system.modules.app.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author tianhh
 * @website https://el-admin.vip
 * @description /
 * @date 2021-05-03
 **/
@Data
public class AppVersionDto implements Serializable {

    private Integer id;

    /**
     * 版本号
     */
    private String versionName;

    /**
     * 打包号
     */
    private String buildCode;

    /**
     * 是否最新 1 历史 0 最新
     */
    private Boolean isNew;

    /**
     * 1 表示删除，0 表示未删除。
     */
    private Boolean isDeleted;

    /**
     * 升级说明
     */
    private String content;

    /**
     * 下载链接
     */
    private String url;

    /**
     * 是否最新 1.历史 0 最新
     */
    private Integer createDate;

    private Timestamp updateDate;

    /**
     * 是否是必须更新
     */
    private Boolean isMust;
    /**
     * 资源类型 1、app 2、wgt
     */
    private Integer resType;
}
