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
package me.zhengjie.modules.smsServer.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author wh
 * @website https://el-admin.vip
 * @description /
 * @date 2021-04-21
 **/
@Data
public class ProjectInformationDto implements Serializable {

    private Integer id;

    private String entryName;

    /**
     * id
     */
    private String appid;

    private String spreat;

    private String spreat1;

    private String spreat2;

    private String spreat3;

    /**
     * 密钥
     */
    private String secret;

    /**
     * 应用状态 （0 不允许发送  1 允许发送）
     */
    private String status;

    private Timestamp createTime;
}
