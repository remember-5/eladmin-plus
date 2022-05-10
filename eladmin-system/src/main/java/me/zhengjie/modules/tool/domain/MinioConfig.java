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

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author fly
* @date 2022-05-10
**/
@Entity
@Data
@Table(name="tool_minio_config")
public class MinioConfig implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "自增主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "host")
    @ApiModelProperty(value = "地址")
    private String host;

    @Column(name = "bucket")
    @ApiModelProperty(value = "空间名称")
    private String bucket;

    @Column(name = "access_key")
    @ApiModelProperty(value = "akey")
    private String accessKey;

    @Column(name = "secret_key")
    @ApiModelProperty(value = "skey")
    private String secretKey;

    public void copy(MinioConfig source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
