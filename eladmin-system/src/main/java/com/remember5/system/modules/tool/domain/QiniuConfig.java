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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 七牛云对象存储配置类
 *
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Data
@TableName("tool_qiniu_config")
public class QiniuConfig implements Serializable {

    @TableId("config_id")
    private Long id;

    @NotBlank
    @Schema(description = "accessKey")
    private String accessKey;

    @NotBlank
    @Schema(description = "secretKey")
    private String secretKey;

    @NotBlank
    @Schema(description = "存储空间名称作为唯一的 Bucket 识别符")
    private String bucket;

    /**
     * Zone表示与机房的对应关系
     * 华东	Zone.zone0()
     * 华北	Zone.zone1()
     * 华南	Zone.zone2()
     * 北美	Zone.zoneNa0()
     * 东南亚	Zone.zoneAs0()
     */
    @NotBlank
    @Schema(description = "Zone表示与机房的对应关系")
    private String zone;

    @NotBlank
    @Schema(description = "外链域名，可自定义，需在七牛云绑定")
    private String host;

    @Schema(description = "空间类型：公开/私有")
    private String type = "公开";
}
