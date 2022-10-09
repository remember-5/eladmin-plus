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
package com.remember5.biz.app.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author tianhh
 * @website https://el-admin.vip
 * @description /
 * @date 2021-05-03
 **/
@Entity
@Data
@Table(name = "app_version")
public class AppVersion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "version_name", nullable = false)
    @NotBlank
    @ApiModelProperty(value = "版本号")
    private String versionName;

    @Column(name = "build_code", nullable = false)
    @NotBlank
    @ApiModelProperty(value = "打包号")
    private String buildCode;

    @Column(name = "is_new", nullable = false)
    @NotBlank
    @ApiModelProperty(value = "是否最新 1 历史 0 最新")
    private Boolean isNew;

    @Column(name = "is_deleted")
    @ApiModelProperty(value = "1 表示删除，0 表示未删除。")
    private Boolean isDeleted;

    @Column(name = "content")
    @ApiModelProperty(value = "升级说明")
    private String content;

    @Column(name = "url", nullable = false)
    @NotBlank
    @ApiModelProperty(value = "下载链接")
    private String url;

    @Column(name = "create_date", nullable = false)
    @ApiModelProperty(value = "是否最新 1.历史 0 最新")
    private Integer createDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    @ApiModelProperty(value = "updateDate")
    private Timestamp updateDate;

    // 1是升级 0是非必须
    @Column(name = "is_must", nullable = false)
    @NotNull
    @ApiModelProperty(value = "是否是必须更新")
    private Boolean isMust;
    /**
     * 资源类型 1、app 2、wgt
     */
    @Column(name = "res_type")
    @ApiModelProperty(value = "资源类型")
    private Integer resType;

    public void copy(AppVersion source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
