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

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @author fly
 * @website https://el-admin.vip
 * @description /
 * @date 2021-04-12
 **/
@Data
public class ResourcesManagementDto implements Serializable {

    private Long id;

    /**
     * 分类
     */
    private Integer type;

    /**
     * 资源编号
     */
    private String num;

    /**
     * 资源地址
     */
    private String url;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 空间名
     */
    private String bucket;

    /**
     * accessKey
     */
    private String accesskey;

    /**
     * secretKey
     */
    private String secretkey;

    /**
     * 启用状态(1启用/0禁用)
     */
    private Integer enabled;

    /**
     * 是否需要自定义前缀(1是/0否)
     */
    private Integer isPrefix;

    /**
     * 自定义前缀
     */
    private String prefixStr;

    /**
     * 是否为https(1是/0否)
     */
    private Integer isHttps;

    /**
     * 备注
     */
    private String remarks;

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
}
