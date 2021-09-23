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
package me.zhengjie.modules.tool.domain;

import lombok.*;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @author fly
 * @website https://el-admin.vip
 * @description /
 * @date 2021-04-12
 **/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_resources_management")
public class ResourcesManagement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "type")
    @ApiModelProperty(value = "分类")
    private Integer type;

    @Column(name = "num")
    @ApiModelProperty(value = "资源编号")
    private String num;

    @Column(name = "url")
    @ApiModelProperty(value = "资源地址")
    private String url;

    @Column(name = "port")
    @ApiModelProperty(value = "端口号")
    private Integer port;

    @Column(name = "bucket")
    @ApiModelProperty(value = "空间名")
    private String bucket;

    @Column(name = "accesskey")
    @ApiModelProperty(value = "accessKey")
    private String accesskey;

    @Column(name = "secretkey")
    @ApiModelProperty(value = "secretKey")
    private String secretkey;

    @Column(name = "enabled")
    @ApiModelProperty(value = "启用状态(1启用/0禁用)")
    private Integer enabled;

    @Column(name = "is_prefix")
    @ApiModelProperty(value = "是否需要自定义前缀(1是/0否)")
    private Integer isPrefix;

    @Column(name = "prefix_str")
    @ApiModelProperty(value = "自定义前缀")
    private String prefixStr;

    @Column(name = "is_https")
    @ApiModelProperty(value = "是否为https(1是/0否)")
    private Integer isHttps;

    @Column(name = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;

    @Column(name = "create_date")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    @ApiModelProperty(value = "修改时间")
    private Timestamp updateDate;

    @Column(name = "is_deleted")
    @ApiModelProperty(value = "1 表示删除，0 表示未删除")
    private Integer isDeleted;

    public void copy(ResourcesManagement source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
