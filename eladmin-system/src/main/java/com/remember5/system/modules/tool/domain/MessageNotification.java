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
package com.remember5.system.modules.tool.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author fly
 * @website https://el-admin.vip
 * @description /
 * @date 2021-04-19
 **/
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_message_notification")
public class MessageNotification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "id")
    private Long id;

    @Column(name = "user_id")
    @Schema(description = "消息通知的用户")
    private Long userId;

    @Column(name = "briefly")
    @Schema(description = "内容简要")
    private String briefly;

    @Column(name = "details")
    @Schema(description = "内容详情")
    private String details;

    @Column(name = "message_level")
    @Schema(description = "消息级别(0紧急/1普通)")
    private Integer messageLevel;

    @Column(name = "message_label")
    @Schema(description = "标签(类型:0错误/1普通通知/2待办)")
    private Integer messageLabel;

    @Column(name = "message_state")
    @Schema(description = "状态(0未开始/1进行中/2已处理)")
    private Integer messageState;

    @Column(name = "create_date")
    @CreationTimestamp
    @Schema(description = "创建时间")
    private Timestamp createDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    @Schema(description = "修改时间")
    private Timestamp updateDate;

    @Column(name = "is_deleted")
    @Schema(description = "1 表示删除，0 表示未删除")
    private Integer isDeleted;

    @Column(name = "by1")
    @Schema(description = "by1")
    private String by1;

    @Column(name = "by2")
    @Schema(description = "by2")
    private String by2;

    @Column(name = "by3")
    @Schema(description = "by3")
    private String by3;

    @Column(name = "by4")
    @Schema(description = "by4")
    private String by4;

    @Column(name = "by5")
    @Schema(description = "by5")
    private String by5;

    public void copy(MessageNotification source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
