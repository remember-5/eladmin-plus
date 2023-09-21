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
package com.remember5.system.modules.column.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author fly
 * @website https://el-admin.vip
 * @description /
 * @date 2021-03-02
 **/
@Entity
@Data
@Table(name = "t_cms_column")
public class CmsColumn implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id")
    @Schema(description = "id")
    private Long id;

    @Column(name = "fid")
    @Schema(description = "父id")
    private Long fid;

    @Column(name = "column_name")
    @Schema(description = "栏目名")
    private String columnName;

    @Column(name = "agent_id")
    @Schema(description = "代理商id")
    private Long agentId;

    @Column(name = "create_time")
    @CreationTimestamp
    @Schema(description = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @Schema(description = "修改时间")
    private Timestamp updateTime;

    @Column(name = "is_deleted")
    @Schema(description = "1 表示删除，0 表示未删除")
    private Boolean isDeleted;

    public void copy(CmsColumn source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
