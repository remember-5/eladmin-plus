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
 * @date 2021-04-19
 **/
@Data
public class SmsRecordaDto implements Serializable {

    private Integer id;

    /**
     * 项目名称
     */
    private String entryName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 发送时间
     */
    private Timestamp sendTime;

    /**
     * 发送状态 0 待发送  1 已发送  2 发送失败
     */
    private String sendStatus;

    private String uid;

    private String spreat1;

    private String spreat2;

    private String spreat3;

    private String appid;

    private String content;
    private String sign;
}
