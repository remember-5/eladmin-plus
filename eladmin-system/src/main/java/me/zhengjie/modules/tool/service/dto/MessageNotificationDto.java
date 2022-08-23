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
package me.zhengjie.modules.tool.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author fly
 * @website https://el-admin.vip
 * @description /
 * @date 2021-04-19
 **/
@Data
public class MessageNotificationDto implements Serializable {

    private Long id;

    /**
     * 消息通知的用户
     */
    private Long userId;

    /**
     * 内容简要
     */
    private String briefly;

    /**
     * 内容详情
     */
    private String details;

    /**
     * 消息级别(0紧急/1普通)
     */
    private Integer messageLevel;

    /**
     * 标签(类型:0错误/1普通通知/2待办)
     */
    private Integer messageLabel;

    /**
     * 状态(0未开始/1进行中/2已处理)
     */
    private Integer messageState;

    /**
     * 创建时间
     */
    private Timestamp createDate;

    /**
     * 修改时间
     */
    private Timestamp updateDate;

    /**
     * 1 表示删除，0 表示未删除
     */
    private Integer isDeleted;

    private String by1;

    private String by2;

    private String by3;

    private String by4;

    private String by5;
}
