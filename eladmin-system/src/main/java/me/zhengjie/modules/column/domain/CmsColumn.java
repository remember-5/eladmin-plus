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
package me.zhengjie.modules.column.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "fid")
    @ApiModelProperty(value = "父id")
    private Long fid;

    @Column(name = "column_name")
    @ApiModelProperty(value = "栏目名")
    private String columnName;

    @Column(name = "agent_id")
    @ApiModelProperty(value = "代理商id")
    private Long agentId;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "修改时间")
    private Timestamp updateTime;

    @Column(name = "is_deleted")
    @ApiModelProperty(value = "1 表示删除，0 表示未删除")
    private Boolean isDeleted;

    @Column(name = "by1")
    @ApiModelProperty(value = "备用字段")
    private String by1;

    @Column(name = "by2")
    @ApiModelProperty(value = "备用字段")
    private String by2;

    @Column(name = "by3")
    @ApiModelProperty(value = "备用字段")
    private String by3;

    @Column(name = "by4")
    @ApiModelProperty(value = "备用字段")
    private String by4;

    @Column(name = "by5")
    @ApiModelProperty(value = "备用字段")
    private String by5;


    public void copy(CmsColumn source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
