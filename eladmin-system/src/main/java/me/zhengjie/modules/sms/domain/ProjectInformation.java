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
package me.zhengjie.modules.sms.domain;

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
* @author wh
* @date 2021-04-19
**/
@Entity
@Data
@Table(name="project_information")
public class ProjectInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "entry_name")
    @ApiModelProperty(value = "entryName")
    private String entryName;

    @Column(name = "appid")
    @ApiModelProperty(value = "id")
    private String appid;

    @Column(name = "spreat")
    @ApiModelProperty(value = "spreat")
    private String spreat;

    @Column(name = "spreat1")
    @ApiModelProperty(value = "spreat1")
    private String spreat1;

    @Column(name = "spreat2")
    @ApiModelProperty(value = "spreat2")
    private String spreat2;

    @Column(name = "spreat3")
    @ApiModelProperty(value = "spreat3")
    private String spreat3;

    @Column(name = "secret")
    @ApiModelProperty(value = "secret")
    private String secret;

    public void copy(ProjectInformation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
